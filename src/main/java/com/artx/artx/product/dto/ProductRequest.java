package com.artx.artx.product.dto;

import lombok.Getter;

import java.util.UUID;

public class ProductRequest {

	@Getter
	public static class Create{
		private UUID userId;
		private Long productCategoryId;
		private String productTitle;
		private String productDescription;
		private Long productQuantity;
		private Long productPrice;
	}

	@Getter
	public static class Read{
		private UUID userId;
	}

}
