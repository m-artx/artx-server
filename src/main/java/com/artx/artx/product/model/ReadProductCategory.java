package com.artx.artx.product.model;

import com.artx.artx.product.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ReadProductCategory {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class ResponseAll {

		@Schema(description = "작품 카테고리 고유 식별 번호", nullable = false, example = "1")
		private Long productCategoryId;
		@Schema(description = "작품 카테고리명", nullable = false, example = "ART")
		private String productCategoryName;
		@Schema(description = "작품 카테고리 상세 설명", nullable = false, example = "다양한 그림을 위한 카테고리입니다.")
		private String productCategoryDescription;
		@Schema(description = "작품 카테고리 대표 이미지", nullable = false, example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productCategoryRepresentativeImage;

		public static ResponseAll from(String imagesApiAddress , ProductCategory productCategory){
			return ResponseAll.builder()
					.productCategoryId(productCategory.getId())
					.productCategoryName(productCategory.getName())
					.productCategoryDescription(productCategory.getDescription())
					.productCategoryRepresentativeImage(imagesApiAddress + productCategory.getProductCategoryImage().getRepresentativeImage())
					.build();
		}
	}
}
