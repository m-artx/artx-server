package com.artx.artx.order.entity;

import com.artx.artx.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

	@EmbeddedId
	private OrderProductId orderProductId;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@MapsId("orderId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private long quantity;

	public void setOrder(Order order) {
		this.order = order;
	}

	public static OrderProduct from(OrderProductId orderProductId, Order order, Product product, Long quantity){
		return OrderProduct.builder()
				.orderProductId(orderProductId)
				.quantity(quantity)
				.order(order)
				.product(product)
				.build();
	}
}
