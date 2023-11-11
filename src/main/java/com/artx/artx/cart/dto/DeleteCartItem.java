package com.artx.artx.cart.dto;

import lombok.Getter;

import java.util.List;

public class DeleteCartItem {

	@Getter
	public static class Request{
		private List<Long> productIds;
	}
}
