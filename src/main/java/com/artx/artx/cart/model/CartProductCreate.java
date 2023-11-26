package com.artx.artx.cart.model;

import com.artx.artx.cart.entity.CartProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CartProductCreate {

	@Getter
	public static class Request {
		@Schema(description = "작품 고유 식별 번호", example = "1")
		@NotNull
		private Long productId;
	}

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "장바구니 고유 식별 번호", example = "1")
		private Long cartId;
		@Schema(description = "작품 고유 식별 번호", example = "1")
		private Long productId;
		@Schema(description = "장바구니 작품 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime createdAt;

		public static Response of(CartProduct cartProduct) {
			return CartProductCreate.Response.builder()
					.cartId(cartProduct.getCartProductId().getCartId())
					.productId(cartProduct.getCartProductId().getProductId())
					.createdAt(cartProduct.getCreatedAt())
					.build();
		}
	}

}
