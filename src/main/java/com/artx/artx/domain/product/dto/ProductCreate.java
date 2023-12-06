package com.artx.artx.domain.product.dto;

import com.artx.artx.domain.product.domain.Product;
import com.artx.artx.domain.product.domain.ProductCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ProductCreate {

	@Getter
	public static class Request{

		@Schema(description = "작품 카테고리 고유 식별 번호", example = "1")
		@NotNull
		private ProductCategoryType productCategory;

		@Schema(description = "작품 소개 제목", example = "목탄으로 표현한 어둠 속에 피어난 장미")
		@NotBlank
		private String productTitle;

		@Schema(description = "작품 상세 설명", example = "10년 전 우연히 길을 지나가다 발견한 장미의 낯빛이 어두웠습니다. 그때의 감정을..")
		@NotBlank
		private String productDescription;

		@Schema(description = "작품 재고 수량", example = "100")
		@NotNull
		private Long productStockQuantity;

		@Schema(description = "작품 가격", example = "100000")
		@NotNull
		private Long productPrice;

		@Schema(description = "작품 이미지", example = "[temp_imageAF4E2B.png, temp_imageAE1BE0.png]")
		private List<String> productImages;

	}

	@Getter
	@Builder
	public static class Response {

		@Schema(description = "작품 고유 식별 번호", example = "1")
		private Long productId;

		@Schema(description = "작품 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime productCreatedAt;

		public static Response of(Product product) {
			return Response.builder()
					.productId(product.getId())
					.productCreatedAt(product.getCreatedAt())
					.build();
		}

	}

}
