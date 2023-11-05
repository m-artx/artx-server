package com.artx.artx.cart.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {

	@Getter
	@Builder
	public static class Create{
		private Long productId;
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	public static class ReadAll{
		private Long cartId;
		private List<Read> cartItems;
	}

	@Getter
	@Builder
	public static class Read{
		private Long productId;
		private String productRepresentativeImage;
		private String productTitle;
		private Long quantity;
		private Long price;
	}
}
