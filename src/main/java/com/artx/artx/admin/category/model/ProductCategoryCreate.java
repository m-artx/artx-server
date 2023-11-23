package com.artx.artx.admin.category.model;

import com.artx.artx.customer.product.type.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductCategoryCreate {

	@Getter
	public static class Request {
		@Schema(description = "작품 카테고리명", example = "ART")
		@NotNull
		private Category productCategory;
		@Schema(description = "작품 카테고리 상세 설명", example = "다양한 그림을 위한 카테고리입니다.")
		@NotNull
		private String productCategoryDescription;
	}

	@Getter
	public static class Response {
		private LocalDateTime createdAt;
	}

}
