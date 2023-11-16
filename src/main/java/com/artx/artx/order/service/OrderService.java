package com.artx.artx.order.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.order.entity.OrderProductId;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.model.OrderProductIdAndQuantity;
import com.artx.artx.order.model.ReadOrder;
import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.CancelPayment;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.payment.repository.PaymentRepository;
import com.artx.artx.payment.service.KakaoPayService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductStock;
import com.artx.artx.product.service.ProductService;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;
	private final UserService userService;
	private final ProductService productService;
	private final KakaoPayService kakaoPayService;

	@Transactional
	public CreatePayment.ReadyResponse createOrder(CreateOrder.Request request) {
		User user = userService.getUserByUserId(request.getUserId());
		Map<Long, Long> productIdsAndQuantities = extractProductIdsAndQuantities(request);
		List<Product> products = productService.getAllProductByIds(productIdsAndQuantities.keySet().stream().toList());
		List<ProductStock> productStocks = products.stream().map(Product::getProductStock).collect(Collectors.toList());

		if (products.isEmpty()) {
			throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
		}

		//재고 확인
		productStocks.stream().forEach(productStock -> {
			if (productStock.getQuantity() < productIdsAndQuantities.get(productStock.getProduct().getId())) {
				throw new BusinessException(ErrorCode.NOT_ENOUGH_QUANTITY);
			}
		});

		String orderTitle = extractOrderTitle(products);
		Order order = orderRepository.save(
				Order.builder()
						.title(orderTitle)
						.status(OrderStatus.ORDER_READY)
						.user(user)
						.totalAmount(products.stream().mapToLong(product -> product.getPrice() * productIdsAndQuantities.get(product.getId())).sum())
						.build()
		);

		products.stream().forEach(product -> {
			//주문 연관 관계
			order.addOrderProduct(OrderProduct.from(
					OrderProductId.builder()
							.productId(product.getId())
							.orderId(order.getId())
							.build(),
					order,
					product,
					productIdsAndQuantities.get(product.getId())
			));
		});

		try {
			//재고 수량 감소 가능 확인
			productStocks.stream().forEach(productStock -> {
				productStock.canDecrease(productIdsAndQuantities.get(productStock.getProduct().getId()));
			});

			CreatePayment.ReadyResponse readyResponse = kakaoPayService.readyPayment(order);
			order.setDelivery(Delivery.from(request));
			return readyResponse;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private String extractOrderTitle(List<Product> products) {
		String representativeProductName = products.get(0).getTitle();
		Integer orderProductsSize = products.size();

		String orderTitle = orderProductsSize > 1 ?
				representativeProductName + " 외 " + (orderProductsSize - 1) + "개의 작품" :
				representativeProductName;
		return orderTitle;
	}

	private Map<Long, Long> extractProductIdsAndQuantities(CreateOrder.Request request) {
		return request.getOrderDetails().stream()
				.collect(Collectors.toMap(OrderProductIdAndQuantity::getProductId, OrderProductIdAndQuantity::getProductQuantity));
	}

	@Transactional(readOnly = true)
	public Page<ReadOrder.ResponseAll> readOrder(ReadOrder.Request request, Pageable pageable) {
		Page<Order> order = orderRepository.findByUserIdWithOrderProductsAndDelivery(request.getUserId(), pageable);
		return order.map(ReadOrder.ResponseAll::from);
	}

	@Transactional
	public CancelPayment cancelOrder(String orderId) {
		Order order = orderRepository.findByUserIdWithPayment(orderId).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
		Payment payment = paymentRepository.findByOrder_Id(orderId).orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));

		Delivery delivery = order.getDelivery();

		delivery.isCacnelable();
		order.isCancelable();
		payment.isCancelable();

		try {
			CancelPayment response = kakaoPayService.cancelPayment(payment);
			List<OrderProduct> orderProducts = order.getOrderProducts();
			List<ProductStock> productStocks = orderProducts.stream().map(OrderProduct::getProduct).map(Product::getProductStock).collect(Collectors.toList());
			Map<Long, ProductStock> productIdsAndStocks = productStocks.stream().collect(Collectors.toMap((productStock -> productStock.getProduct().getId()), (productStock -> productStock)));

			orderProducts.stream().forEach(orderProduct -> {
				ProductStock productStock = productIdsAndStocks.get(orderProduct.getProduct().getId());
				productStock.increase(orderProduct.getQuantity());
			});
			return response;

		} catch (Exception e) {

		}
		return null;
	}

	@Transactional(readOnly = true)
	public Map<OrderStatus, Long> readAllOrderStatusCounts() {

		List<Object[]> statusCounts = orderRepository.getAllOrderStatusCounts();
		Map<OrderStatus, Long> map = new HashMap<>();

		for (Object[] statusCount : statusCounts) {
			map.put((OrderStatus) statusCount[0], (Long) statusCount[1]);
		}
		return map;
	}

	public Map<String, Long> readAllMonthlyOrderCounts() {

		LocalDateTime startDateTime = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusDays(1);

		List<Object[]> orderCounts = orderRepository.getAllMontlyOrderCounts(startDateTime, endDateTime);

		Map<String, Long> map = new HashMap<>();

		map.put("previousMonth", (Long) orderCounts.get(0)[1]);
		map.put("presentMonth", (Long) orderCounts.get(1)[1]);

		return map;
	}

	public Map<String, Long> readAllYearlyOrderCounts() {

		LocalDateTime startDateTime = LocalDateTime.now().minusYears(1).withDayOfYear(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusYears(1).withDayOfYear(1).minusDays(1);

		List<Object[]> orderCounts = orderRepository.getAllYearlyOrderCounts(startDateTime, endDateTime);

		Map<String, Long> map = new HashMap<>();

		if (orderCounts.size() == 2) {
			map.put("previousYear", (Long) orderCounts.get(0)[1]);
			map.put("presentYear", (Long) orderCounts.get(1)[1]);
			return map;
		}

		if (orderCounts.size() == 1) {
			map.put("presentYear", (Long) orderCounts.get(0)[1]);
			return map;
		}

		return map;

	}
}
