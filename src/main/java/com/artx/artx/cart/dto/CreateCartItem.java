package com.artx.artx.cart.dto;

import com.artx.artx.cart.model.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CreateCartItem {

	@Getter
	public static class Request {
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
	}

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "장바구니 고유 식별 번호", nullable = false, example = "1")
		private Long cartId;
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
		@Schema(description = "장바구니 작품 등록 시간", nullable = false, example = "2023-01-01T10:00:30")
		private LocalDateTime cartItemCreatedAt;

		public static Response from(CartItem cartItem) {
			return CreateCartItem.Response.builder()
					.cartId(cartItem.getCartItemId().getCartId())
					.productId(cartItem.getCartItemId().getProductId())
					.cartItemCreatedAt(cartItem.getCreatedAt())
					.build();
		}
	}

}
