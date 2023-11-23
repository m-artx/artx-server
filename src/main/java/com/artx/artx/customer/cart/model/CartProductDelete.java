package com.artx.artx.customer.cart.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public class CartProductDelete {

	@Getter
	public static class Request{
		@NotNull
		@Valid
		private List<Long> productIds;
	}
}
