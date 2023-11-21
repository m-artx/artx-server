package com.artx.artx.order.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.order.entity.OrderProductId;
import com.artx.artx.order.model.OrderCreate;
import com.artx.artx.order.model.OrderProductIdAndQuantity;
import com.artx.artx.order.model.OrderRead;
import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.payment.entity.KakaoPayment;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.PaymentCancel;
import com.artx.artx.payment.model.PaymentCreate;
import com.artx.artx.payment.model.kakaopay.KakaoPaymentCancel;
import com.artx.artx.payment.repository.KakaoPaymentRepository;
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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final KakaoPaymentRepository kakaoPaymentRepository;
	private final UserService userService;
	private final ProductService productService;
	private final KakaoPayService kakaoPayService;

	@Transactional
	public PaymentCreate.Response createOrder(UUID userId, OrderCreate.Request request) {
		User user = userService.getUserByUserId(userId);
		Map<Long, Long> productIdsAndQuantities = extractProductIdsAndQuantities(request);
		List<Product> products = productService.getAllProductByIds(new ArrayList<>(productIdsAndQuantities.keySet()));
		List<ProductStock> productStocks = products.stream().map(Product::getProductStock).collect(Collectors.toList());

		Order order = orderRepository.save(Order.from(user, request));

		checkIfProductsExist(products);
		checkIfEnoughQuantity(products, productIdsAndQuantities);

		products.stream().forEach(product ->
			order.addOrderProduct(
					OrderProduct.from(
							OrderProductId.from(product, order),
							order,
							product,
							productIdsAndQuantities.get(product.getId())
					)
			));

		try {
			productStocks.stream().forEach(productStock -> productStock.isQuantityLessThan(productIdsAndQuantities.get(productStock.getProduct().getId())));
			PaymentCreate.Response readyResponse = kakaoPayService.processPayment(order);
			orderRepository.save(order);
			return readyResponse;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Page<AbstractMap.SimpleEntry<LocalDate, OrderRead.SummaryResponse>> readAllOrders(UUID userId, Pageable pageable) {
		Page<Order> orders = orderRepository.fetchByUserIdWithOrderProducts(userId, pageable);
		Page<AbstractMap.SimpleEntry<LocalDate, OrderRead.SummaryResponse>> orderDetailMap =
				orders.map(order -> {
					LocalDate date = order.getCreatedAt().toLocalDate();
					OrderRead.SummaryResponse orderReadDeatilResponse = OrderRead.SummaryResponse.of(order);
					return new AbstractMap.SimpleEntry<>(date, orderReadDeatilResponse);
				});

		return orderDetailMap;
	}

	@Transactional
	public PaymentCancel.Response cancelOrder(String orderId) {
		KakaoPayment kakaoPayment = kakaoPaymentRepository.fetchKakaoPaymentByOrderId(orderId).orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));

		Payment payment = kakaoPayment.getPayment();
		Order order = payment.getOrder();


//		delivery.isCacnelable();
		order.isCancelable();
		payment.isCancelable();

		try {
			KakaoPaymentCancel.Response response = kakaoPayService.cancelPayment(order.getId());
			List<OrderProduct> orderProducts = order.getOrderProducts();
			orderProducts.stream().forEach(OrderProduct::increaseOrderProductStocks);

			return response;
		} catch (Exception e) {

		}
		return null;
	}

	@Transactional(readOnly = true)
	public OrderRead.DetailResponse readOrders(UUID userId, String orderId) {
		Order order = orderRepository.fetchByUserIdWithPayment(userId, orderId).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
		return OrderRead.DetailResponse.of(order);
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

	private Map<Long, Long> extractProductIdsAndQuantities(OrderCreate.Request request) {
		return request.getOrderProductDetails().stream()
				.collect(Collectors.toMap(OrderProductIdAndQuantity::getProductId, OrderProductIdAndQuantity::getProductQuantity));
	}

}