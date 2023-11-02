package com.artx.artx.product.model;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		private List<String> productImageUrls;
		private String productTitle;
		private String productDescription;
		private Long productQuantity;
		private Long productPrice;
		private LocalDate createdAt;

		public static Read from(String serverUrl, Product product) {
			List<String> fileImageNames = product.getProductImages().stream().map(ProductImage::getName).collect(Collectors.toList());
			StringBuilder sb = new StringBuilder();
			sb.append(serverUrl);
			sb.append("/api/images/");

			int initLength = sb.length();

			List<String> fileImageUrls = new ArrayList<>();

			for (int i = 0; i < fileImageNames.size(); i++) {
				sb.append(fileImageNames.get(i));
				fileImageUrls.add(sb.toString());
				sb.setLength(initLength);
			}

			return Read.builder().productId(product.getId()).productTitle(product.getTitle())
					.productDescription(product.getDescription())
					.productPrice(product.getPrice())
					.productQuantity(product.getQuantity())
					.productImageUrls(fileImageUrls)
					.createdAt(LocalDate.from(product.getCreatedAt()))
					.build();
		}
	}

	@Getter
	@Builder
	public static class ReadAll {
		private Long productId;
		private String productImageUrl;
		private String productTitle;

		public static ReadAll from(String serverUrl, Product product) {

			StringBuilder sb = new StringBuilder();

			return ReadAll.builder()
					.productId(product.getId())
					.productImageUrl(sb.append(serverUrl).append("/api/images/").append(product.getRepresentativeImage()).toString())
					.productTitle(product.getTitle())
					.build();
		}
	}
}
