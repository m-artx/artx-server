package com.artx.artx.cart.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartItemId implements Serializable {

	private Long cartId;
	private Long productId;

}
