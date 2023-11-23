package com.artx.artx.customer.cart.model;

import lombok.Getter;

public class CartProductIncrease {

	@Getter
	public static class Request {
		private Long productId;
	}

}
