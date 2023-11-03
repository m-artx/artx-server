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
		private List<String> productImages;
		private String productTitle;
		private String productDescription;
		private Long productQuantity;
		private Long productPrice;
		private LocalDate createdAt;

		public static Read from(String apiAddress, Product product) {
			List<String> fileImageNames = product.getProductImages().stream().map(ProductImage::getName).collect(Collectors.toList());
			List<String> fileImageUrls = new ArrayList<>();

			for (int i = 0; i < fileImageNames.size(); i++) {
				fileImageUrls.add(apiAddress + fileImageNames.get(i));
			}

			return Read.builder().productId(product.getId()).productTitle(product.getTitle())
					.productDescription(product.getDescription())
					.productPrice(product.getPrice())
					.productQuantity(product.getQuantity())
					.productImages(fileImageUrls)
					.createdAt(LocalDate.from(product.getCreatedAt()))
					.build();
		}
	}

	@Getter
	@Builder
	public static class ReadAll {
		private Long productId;
		private String productRepresentativeImage;
		private String productTitle;
		private String link;

		public static ReadAll from(String imageApiAddress, String productApiAddress, Product product) {
			return ReadAll.builder()
					.productId(product.getId())
					.productRepresentativeImage(imageApiAddress + product.getRepresentativeImage())
					.productTitle(product.getTitle())
					.link(productApiAddress + product.getId())
					.build();
		}
	}


}
