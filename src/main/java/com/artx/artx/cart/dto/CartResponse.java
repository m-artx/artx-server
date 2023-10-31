package com.artx.artx.cart.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CartResponse {

	@Getter
	@Builder
	public static class Create{
		private Long productId;
		private LocalDateTime savedDate;
	}
}
