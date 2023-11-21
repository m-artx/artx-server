package com.artx.artx.product.model;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.type.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductCreate {

	@Getter
	public static class Request{
		@Schema(description = "작품 카테고리 고유 식별 번호", example = "1")
		private Category productCategory;

		@Schema(description = "작품 소개 제목", example = "목탄으로 표현한 어둠 속에 피어난 장미")
		private String productTitle;

		@Schema(description = "작품 상세 설명", example = "10년 전 우연히 길을 지나가다 발견한 장미의 낯빛이 어두웠습니다. 그때의 감정을..")
		private String productDescription;

		@Schema(description = "작품 재고 수량", example = "100")
		private Long productStockQuantity;

		@Schema(description = "작품 가격", example = "100000")
		private Long productPrice;

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
