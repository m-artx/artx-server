package com.artx.artx.order.entity;

import com.artx.artx.product.entity.Product;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductId implements Serializable {

	private Long productId;
	private String orderId;

	public static OrderProductId from(Product product, Order order){
		return OrderProductId.builder()
				.productId(product.getId())
				.orderId(order.getId())
				.build();
	}
}
