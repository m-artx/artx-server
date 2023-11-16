package com.artx.artx.product.model;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.type.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateProduct {

	@Getter
	public static class Request{
		//TODO: 가로, 세로 입력 받아야 할 것

		@Schema(description = "유저 고유 식별 번호", nullable = false, example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "작품 카테고리 고유 식별 번호", nullable = false, example = "1")
		private Category productCategory;
		@Schema(description = "작품 소개 제목", nullable = false, example = "목탄으로 표현한 어둠 속에 피어난 장미")
		private String productTitle;
		@Schema(description = "작품 상세 설명", nullable = false, example = "10년 전 우연히 길을 지나가다 발견한 장미의 낯빛이 어두웠습니다. 그때의 감정을..")
		private String productDescription;
		@Schema(description = "작품 재고 수량", nullable = false, example = "100")
		private Long productQuantity;
		@Schema(description = "작품 가격", nullable = false, example = "100000")
		private Long productPrice;

	}

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
		@Schema(description = "작품 등록 시간", nullable = false, example = "2023-01-01T10:00:30")
		private LocalDateTime createdAt;

		public static Response from(Product product) {
			return Response.builder()
					.productId(product.getId())
					.createdAt(product.getCreatedAt())
					.build();
		}
	}
}
