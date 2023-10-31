package com.artx.artx.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
	private Long productId;
	private String productTitle;
	private Long productQuantity;
	private Long productPrice;
}
