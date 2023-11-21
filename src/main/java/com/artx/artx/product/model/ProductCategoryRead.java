package com.artx.artx.product.model;

import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.type.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ProductCategoryRead {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class SummaryResponse {

		@Schema(description = "작품 카테고리 고유 식별 번호", example = "1")
		private Long productCategoryId;

		@Schema(description = "작품 카테고리명", example = "ART")
		private Category productCategory;

		@Schema(description = "작품 카테고리 상세 설명", example = "다양한 그림을 위한 카테고리입니다.")
		private String productCategoryDescription;

		@Schema(description = "작품 카테고리 대표 이미지", example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productCategoryRepresentativeImage;

		public static SummaryResponse of(String imagesApiAddress , ProductCategory productCategory){
			return SummaryResponse.builder()
					.productCategoryId(productCategory.getId())
					.productCategory(productCategory.getType())
					.productCategoryDescription(productCategory.getDescription())
					.productCategoryRepresentativeImage(imagesApiAddress + productCategory.getProductCategoryImage().getRepresentativeImage())
					.build();
		}
	}
}
