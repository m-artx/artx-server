package com.artx.artx.cart.dto;

import com.artx.artx.cart.model.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

public class ReadCartItem {

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "장바구니 고유 식별 번호", nullable = false, example = "1")
		private Long cartId;

		@Schema(description = "장바구니 작품 상세 정보", nullable = false, example = "")
		private Page<CartItemDetail> cartItemDetails;

		public static Response from(Long cartId, Page<CartItemDetail> cartItemDetails){
			return Response.builder()
					.cartId(cartId)
					.cartItemDetails(cartItemDetails)
					.build();
		}
	}

	@Getter
	@Builder
	public static class CartItemDetail {

		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
		@Schema(description = "작품 대표 이미지", nullable = false, example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productRepresentativeImage;
		@Schema(description = "작품명", nullable = false, example = "목탄으로 표현한 어둠 속에 피어난 장미")
		private String productTitle;
		@Schema(description = "작품 재고 수량", nullable = false, example = "100")
		private Long productQuantity;
		@Schema(description = "현재 장바구니 수량", nullable = false, example = "100")
		private Long cartProductQuantity;
		@Schema(description = "작품 가격", nullable = false, example = "100000")
		private Long productPrice;

		public static CartItemDetail from(String imagesApiAddress, CartItem cartItem, Long carItemQuantity){
			return CartItemDetail.builder()
					.productId(cartItem.getCartItemId().getProductId())
					.productRepresentativeImage(imagesApiAddress + cartItem.getProduct().getRepresentativeImage())
					.productTitle(cartItem.getProduct().getTitle())
					.productPrice(cartItem.getProduct().getPrice())
					.productQuantity(carItemQuantity)
					.cartProductQuantity(cartItem.getQuantity())
					.build();
		}
	}

}
