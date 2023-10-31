package com.artx.artx.cart.dto;

import lombok.Getter;

public class CartRequest {

	@Getter
	public static class Create{
		private Long productId;
	}

}
