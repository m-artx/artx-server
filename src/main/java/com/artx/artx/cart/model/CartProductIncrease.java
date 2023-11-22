package com.artx.artx.cart.model;

import lombok.Getter;

public class CartProductIncrease {

	@Getter
	public static class Request {
		private Long productId;
	}

}
