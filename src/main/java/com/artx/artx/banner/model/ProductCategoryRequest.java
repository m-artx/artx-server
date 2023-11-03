package com.artx.artx.banner.model;

import lombok.Getter;

public class ProductCategoryRequest {

	@Getter
	public static class Create{
		private String categoryName;
		private String categoryDescription;
	}

}
