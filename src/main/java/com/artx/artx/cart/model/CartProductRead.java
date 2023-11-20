package com.artx.artx.cart.model;

import com.artx.artx.cart.entity.CartProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

public class CartProductRead {

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "장바구니 고유 식별 번호", example = "1")
		private Long cartId;

		@Schema(description = "장바구니 작품 상세 정보", example = "")
		private Page<CartProductDetail> cartProductDetails;

		public static Response from(Long cartId, Page<CartProductDetail> cartProductDetails){
			return Response.builder()
					.cartId(cartId)
					.cartProductDetails(cartProductDetails)
					.build();
		}
	}

	@Getter
	@Builder
	public static class CartProductDetail {

		@Schema(description = "작가 닉네임", example = "이중섭")
		private String artistNickname;
		@Schema(description = "작품 고유 식별 번호", example = "1")
		private Long productId;
		@Schema(description = "작품 대표 이미지", example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productRepresentativeImage;
		@Schema(description = "작품명", example = "목탄으로 표현한 어둠 속에 피어난 장미")
		private String productTitle;
		@Schema(description = "작품 재고 수량", example = "100")
		private Long productQuantity;
		@Schema(description = "현재 장바구니 수량", example = "100")
		private Long cartProductQuantity;
		@Schema(description = "작품 가격", example = "100000")
		private Long productPrice;

		public static CartProductDetail of(String imagesApiAddress, CartProduct cartProduct, Long carProductQuantity){
			return CartProductDetail.builder()
					.artistNickname(cartProduct.getProduct().getUser().getNickname())
					.productId(cartProduct.getCartProductId().getProductId())
					.productRepresentativeImage(imagesApiAddress + cartProduct.getProduct().getRepresentativeImage())
					.productTitle(cartProduct.getProduct().getTitle())
					.productPrice(cartProduct.getProduct().getPrice())
					.productQuantity(carProductQuantity)
					.cartProductQuantity(cartProduct.getQuantity())
					.build();
		}
	}

}
