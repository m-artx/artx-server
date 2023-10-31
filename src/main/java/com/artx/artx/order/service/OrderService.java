package com.artx.artx.order.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.CommonOrder;
import com.artx.artx.order.dto.OrderRequest;
import com.artx.artx.order.dto.OrderResponse;
import com.artx.artx.order.model.Delivery;
import com.artx.artx.order.model.Order;
import com.artx.artx.order.model.OrderProduct;
import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.product.model.Product;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.user.model.User;
import com.artx.artx.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Transactional
	public OrderResponse.Create createOrder(CommonOrder.Create request) {
		User user = getUserById(request.getUserId());
		Map<Long, Long> productIdsAndQuantities = extractProductIdsAndQuantities(request);
		List<Product> products = productRepository.findAllById(productIdsAndQuantities.keySet());
		Stream<Product> productStream = products.stream();

		//재고 확인
		productStream.forEach(product -> {
			if(product.getQuantity() < productIdsAndQuantities.get(product.getId())){
				throw new BusinessException(ErrorCode.NOT_ENOUGH_QUANTITY);
			}
		});

		Order order = orderRepository.save(
				Order.builder()
						.status(OrderStatus.ORDERED)
						.user(user)
						.delivery(Delivery.from(request))
						.price(products.stream().mapToLong(Product::getPrice).sum())
						.build()
		);
		productStream.forEach(product -> {
			//주문 연관 관계
			order.addOrderProduct(OrderProduct.from(order, product, productIdsAndQuantities.get(product.getId())));

			//재고 수량 감소
			product.decrease(productIdsAndQuantities.get(product.getId()));
		});

		return OrderResponse.Create.from(order);
	}

	private Map<Long, Long> extractProductIdsAndQuantities(CommonOrder.Create request) {
		return request.getOrderData().stream().collect(Collectors.toMap(OrderRequest.OrderData::getProductId, OrderRequest.OrderData::getQuantity));
	}


	private User getUserById(UUID userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}
}
