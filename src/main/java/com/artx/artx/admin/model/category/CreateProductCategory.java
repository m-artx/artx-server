package com.artx.artx.admin.model.category;

import com.artx.artx.product.type.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

public class CreateProductCategory {

	@Getter
	public static class Request {
		@Schema(description = "작품 카테고리명", nullable = false, example = "ART")
		private Category productCategory;
		@Schema(description = "작품 카테고리 상세 설명", nullable = false, example = "다양한 그림을 위한 카테고리입니다.")
		private String productCategoryDescription;
	}

	@Getter
	public static class Response {
		private LocalDateTime createdAt;
	}

}
