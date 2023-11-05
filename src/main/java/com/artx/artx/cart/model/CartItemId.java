package com.artx.artx.cart.model;

import com.artx.artx.product.entity.Product;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartItemId implements Serializable {

	private Long cartId;
	private Long productId;

	public static CartItemId from(Cart cart, Product product){
		return CartItemId.builder()
				.cartId(cart.getId())
				.productId(product.getId())
				.build();
	}

	public static CartItemId from(Long cartId, Long productId){
		return CartItemId.builder()
				.cartId(cartId)
				.productId(productId)
				.build();
	}

}
