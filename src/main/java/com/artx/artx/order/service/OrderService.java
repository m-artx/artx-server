package com.artx.artx.order.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.order.entity.Delivery;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.model.OrderProductIdAndQuantity;
import com.artx.artx.order.model.ReadOrder;
import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.CancelPayment;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.payment.service.PaymentService;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserService userService;
	private final ProductService productService;
	private final PaymentService paymentService;

	@Transactional
	public CreatePayment.ReadyResponse createOrder(CreateOrder.Request request) {
		User user = userService.getUserByUserId(request.getUserId());
		Map<Long, Long> productIdsAndQuantities = extractProductIdsAndQuantities(request);
		List<Product> products = productService.getAllProductByIds(productIdsAndQuantities.keySet().stream().toList());
		List<ProductStock> productStocks = products.stream().map(Product::getProductStock).collect(Collectors.toList());

		if(products.isEmpty()){
			throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
		}

		//재고 확인
		productStocks.stream().forEach(productStock -> {
			if(productStock.getQuantity() < productIdsAndQuantities.get(productStock.getProduct().getId())){
				throw new BusinessException(ErrorCode.NOT_ENOUGH_QUANTITY);
			}
		});

		String orderTitle = extractOrderTitle(products);
		Order order = orderRepository.save(
				Order.builder()
						.title(orderTitle)
						.status(OrderStatus.ORDER_READY)
						.user(user)
						.delivery(Delivery.from(request))
						.totalAmount(products.stream().mapToLong(product -> product.getPrice() * productIdsAndQuantities.get(product.getId())).sum())
						.build()
		);

		products.stream().forEach(product -> {
			//주문 연관 관계
			order.addOrderProduct(OrderProduct.from(order, product, productIdsAndQuantities.get(product.getId())));
		});

		try{
			//재고 수량 감소 가능 확인
			productStocks.stream().forEach(productStock -> {
				productStock.canDecrease(productIdsAndQuantities.get(productStock.getProduct().getId()));
			});

			CreatePayment.ReadyResponse readyResponse = paymentService.readyPayment(order);
			return readyResponse;

		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	private String extractOrderTitle(List<Product> products) {
		String representativeProductName = products.get(0).getTitle();
		Integer orderProductsSize = products.size();

		String orderTitle = orderProductsSize > 0 ?
				representativeProductName + " 외 " + (orderProductsSize - 1) +"개의 작품" :
				representativeProductName;
		return orderTitle;
	}

	private Map<Long, Long> extractProductIdsAndQuantities(CreateOrder.Request request) {
		return request.getOrderDetails().stream()
				.collect(Collectors.toMap(OrderProductIdAndQuantity::getProductId, OrderProductIdAndQuantity::getProductQuantity));
	}

	@Transactional(readOnly = true)
	public Page<ReadOrder.ResponseAll> readOrder(ReadOrder.Request request, Pageable pageable) {
		Page<Order> order = orderRepository.findByUserIdWithOrderProductsAndPaymentAndDelivery(request.getUserId(), pageable);
		return order.map(ReadOrder.ResponseAll::from);
	}

	@Transactional
	public CancelPayment cancelOrder(Long orderId) {
		Order order = orderRepository.findByUserIdWithPayment(orderId).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
		Payment payment = order.getPayment();
		Delivery delivery = order.getDelivery();

		delivery.isCacnelable();
		order.isCancelable();
		payment.isCancelable();

		try {
			CancelPayment response = paymentService.cancelPayment(payment);
			List<OrderProduct> orderProducts = order.getOrderProducts();
			List<ProductStock> productStocks = orderProducts.stream().map(OrderProduct::getProduct).map(Product::getProductStock).collect(Collectors.toList());
			Map<Long, ProductStock> productIdsAndStocks = productStocks.stream().collect(Collectors.toMap((productStock -> productStock.getProduct().getId()), (productStock -> productStock)));

			orderProducts.stream().forEach(orderProduct -> {
				ProductStock productStock = productIdsAndStocks.get(orderProduct.getProduct().getId());
				productStock.increase(orderProduct.getQuantity());
			});
			return response;

		}catch (Exception e){

		}
		return null;
	}
}
