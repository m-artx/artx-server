package com.artx.artx.cart.model;

import lombok.Getter;

import java.util.List;

public class CartProductDelete {

	@Getter
	public static class Request{
		private List<Long> productIds;
	}
}
