package com.artx.artx.cart.model;

import lombok.Getter;

import java.util.List;

public class DeleteCartProduct {

	@Getter
	public static class Request{
		private List<Long> productIds;
	}
}
