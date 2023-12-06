package com.artx.artx.domain.cart.dto;

import lombok.Getter;

public class CartProductIncrease {

	@Getter
	public static class Request {
		private Long productId;
	}

}
