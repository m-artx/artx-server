package com.artx.artx.product.dto;

import com.artx.artx.product.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

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
		private Long productId;
		private String productImageUrl;
		private String productTitle;

		public static ReadAll from(String directory, Product product){
			return ReadAll.builder()
					.productId(product.getId())
					.productImageUrl(Path.of(directory, product.getRepresentativeImage()).toString())
					.productTitle(product.getTitle())
					.build();
		}
	}
}
