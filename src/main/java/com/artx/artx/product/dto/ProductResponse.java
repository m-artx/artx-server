package com.artx.artx.product.dto;

import com.artx.artx.product.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProductResponse {

	@Getter
	@Builder
	public static class Create {
		private Long productId;
		private String productTitle;
		private Long productQuantity;
		private Long productPrice;

		public static ProductResponse.Create from(Product product) {
			return Create.builder()
					.productId(product.getId())
					.productTitle(product.getTitle())
					.productPrice(product.getPrice())
					.productQuantity(product.getQuantity())
					.build();
		}
	}

	@Getter
	@Builder
	public static class Read {
		private Long productId;
		private String productTitle;
		private long productQuantity;
		private long productPrice;
	}

	@Getter
	@Builder
	public static class ReadAll {
		List<ProductDto> productList;
	}
}
