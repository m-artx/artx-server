package com.artx.artx.cart.entity;

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
public class CartProductId implements Serializable {

	private Long cartId;
	private Long productId;

	public static CartProductId from(Cart cart, Product product){
		return CartProductId.builder()
				.cartId(cart.getId())
				.productId(product.getId())
				.build();
	}

	public static CartProductId from(Long cartId, Long productId){
		return CartProductId.builder()
				.cartId(cartId)
				.productId(productId)
				.build();
	}

}
