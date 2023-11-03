package com.artx.artx.product.model;

import com.artx.artx.product.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ProductCategoryResponse {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class ReadAll {

		private String categoryName;
		private String categoryDescription;
		private String categoryRepresentativeImage;

		public static ReadAll from(String imagesApiAddress , ProductCategory productCategory){
			return ReadAll.builder()
					.categoryName(productCategory.getName())
					.categoryDescription(productCategory.getDescription())
					.categoryRepresentativeImage(imagesApiAddress + productCategory.getProductCategoryImage().getRepresentativeImage())
					.build();
		}
	}
}
