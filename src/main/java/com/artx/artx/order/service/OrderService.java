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
import com.artx.artx.payment.entity.KakaoPayment;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.CancelPayment;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.payment.model.kakaopay.CancelKakaoPayment;
import com.artx.artx.payment.repository.KakaoPaymentRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;
	private final KakaoPaymentRepository kakaoPaymentRepository;
	private final UserService userService;
	private final ProductService productService;
	private final KakaoPayService kakaoPayService;

	@Transactional
	public CreatePayment.Response createOrder(CreateOrder.Request request) {
		User user = userService.getUserByUserId(request.getUserId());
		Map<Long, Long> productIdsAndQuantities = extractProductIdsAndQuantities(request);
		List<Product> products = productService.getAllProductByIds(new ArrayList<>(productIdsAndQuantities.keySet()));
		List<ProductStock> productStocks = products.stream().map(Product::getProductStock).collect(Collectors.toList());

		Order order = orderRepository.save(Order.from(user));

		checkIfProductsExist(products);
		checkIfEnoughQuantity(products, productIdsAndQuantities);

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
			productStocks.stream().forEach(productStock -> productStock.canDecrease(productIdsAndQuantities.get(productStock.getProduct().getId())));
			CreatePayment.Response readyResponse = kakaoPayService.processPayment(order);
			order.setDelivery(Delivery.from(request));
			return readyResponse;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}



	private void checkIfEnoughQuantity(List<Product> products, Map<Long, Long> productIdsAndQuantities) {
		for (Product product : products) {
			Long quantity = productIdsAndQuantities.get(product.getId());
			if (quantity > product.getProductStock().getQuantity()) {
				throw new BusinessException(ErrorCode.NOT_ENOUGH_QUANTITY);
			}
		}
	}

	private void checkIfProductsExist(List<Product> products) {
		if (products.isEmpty()) {
			throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
		}
	}

	private Map<Long, Long> extractProductIdsAndQuantities(CreateOrder.Request request) {
		return request.getOrderDetails().stream()
				.collect(Collectors.toMap(OrderProductIdAndQuantity::getProductId, OrderProductIdAndQuantity::getProductQuantity));
	}

	@Transactional(readOnly = true)
	public Page<ReadOrder.Response> readAllOrders(UUID userId, Pageable pageable) {
		Page<Order> order = orderRepository.fetchByUserIdWithOrderProductsAndDelivery(userId, pageable);
		return order.map(ReadOrder.Response::from);
	}


	public CancelPayment.Response cancelOrder(String orderId) {
		KakaoPayment kakaoPayment = kakaoPaymentRepository.fetchKakaoPaymentByOrderId(orderId).orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));

		Payment payment = kakaoPayment.getPayment();
		Order order = payment.getOrder();
		Delivery delivery = order.getDelivery();

		delivery.isCacnelable();
		order.isCancelable();
		payment.isCancelable();

		try {
			CancelKakaoPayment.Response response = kakaoPayService.cancelPayment(order.getId());
			List<OrderProduct> orderProducts = order.getOrderProducts();
			orderProducts.stream().forEach(OrderProduct::increaseOrderProductStocks);

			return response;
		} catch (Exception e) {

		}
		return null;
	}

	@Transactional(readOnly = true)
	public Map<OrderStatus, Long> readAllOrderStatusCounts() {

		List<Object[]> statusCounts = orderRepository.countAllOrderStatus();
		Map<OrderStatus, Long> map = new HashMap<>();

		for (Object[] statusCount : statusCounts) {
			map.put((OrderStatus) statusCount[0], (Long) statusCount[1]);
		}
		return map;
	}

	public Map<String, Long> readAllMonthlyOrderCounts() {

		LocalDateTime startDateTime = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusDays(1);

		List<Object[]> orderCounts = orderRepository.countAllMontlyOrder(startDateTime, endDateTime);

		Map<String, Long> map = new HashMap<>();

		map.put("previousMonth", (Long) orderCounts.get(0)[1]);
		map.put("presentMonth", (Long) orderCounts.get(1)[1]);

		return map;
	}

	public Map<String, Long> readAllYearlyOrderCounts() {

		LocalDateTime startDateTime = LocalDateTime.now().minusYears(1).withDayOfYear(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusYears(1).withDayOfYear(1).minusDays(1);

		List<Object[]> orderCounts = orderRepository.countAllYearlyOrder(startDateTime, endDateTime);

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

	@Transactional(readOnly = true)
	public ReadOrder.Response readOrders(UUID userId, String orderId, Pageable pageable) {
		Order order = orderRepository.fetchByUserIdWithPayment(userId, orderId).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
		return ReadOrder.Response.from(order);
	}
}