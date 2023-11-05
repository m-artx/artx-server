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

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private long quantity;

	public void setOrder(Order order) {
		this.order = order;
	}

	public static OrderProduct from(Order order, Product product, Long quantity){
		return OrderProduct.builder()
				.quantity(quantity)
				.order(order)
				.product(product)
				.build();
	}
}
