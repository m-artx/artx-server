package com.artx.artx.customer.cart.model;

import lombok.Getter;

public class CartProductDecrease {

	@Getter
	public static class Request {
		private Long productId;
	}

}
