package com.artx.artx.product.model;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductImage;
import com.artx.artx.product.entity.ProductStock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadProduct {

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
		@Schema(description = "작품 대표 이미지", nullable = false, example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productRepresentativeImage;
		@Schema(description = "작품 전체 이미지", nullable = false, example = "[http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png, ...]")
		private List<String> productImages;
		@Schema(description = "작품명", nullable = false, example = "목탄으로 표현한 어둠 속에 피어난 장미")
		private String productTitle;
		@Schema(description = "작품 상세 설명", nullable = false, example = "10년 전 우연히 길을 지나가다 발견한 장미의 낯빛이 어두웠습니다. 그때의 감정을..")
		private String productDescription;
		@Schema(description = "작품 재고 수량", nullable = false, example = "100")
		private Long productQuantity;
		@Schema(description = "작품 가격", nullable = false, example = "100000")
		private Long productPrice;
		@Schema(description = "작품 등록 시간", nullable = false, example = "2023-01-01T10:00:30")
		private LocalDate productCreatedAt;

		public static Response from(String imagesApiAddress, Product product) {
			List<String> fileImageNames = product.getProductImages().stream().map(ProductImage::getName).collect(Collectors.toList());
			List<String> fileImageUrls = new ArrayList<>();

			for (int i = 0; i < fileImageNames.size(); i++) {
				fileImageUrls.add(imagesApiAddress + fileImageNames.get(i));
			}

			return Response.builder()
					.productId(product.getId())
					.productRepresentativeImage(imagesApiAddress + product.getRepresentativeImage())
					.productImages(fileImageUrls)
					.productTitle(product.getTitle())
					.productDescription(product.getDescription())
					.productPrice(product.getPrice())
					.productQuantity(product.getProductStock().getQuantity())
					.productCreatedAt(LocalDate.from(product.getCreatedAt()))
					.build();
		}
	}

	@Getter
	@Builder
	public static class SimpleResponse {
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
		@Schema(description = "작품 링크", nullable = false, example = "http://127.0.0.1:8080/api/products/1")
		private String productLink;
		@Schema(description = "작품 대표 이미지", nullable = false, example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productRepresentativeImage;
		@Schema(description = "작품명", nullable = false, example = "검은 장미")
		private String productTitle;
		@Schema(description = "작품 가격", nullable = false, example = "100000")
		private Long productPrice;

		public static SimpleResponse from(String imageApiAddress, String productApiAddress, Product product) {
			return SimpleResponse.builder()
					.productId(product.getId())
					.productLink(productApiAddress + product.getId())
					.productRepresentativeImage(imageApiAddress + product.getRepresentativeImage())
					.productTitle(product.getTitle())
					.productPrice(product.getPrice())
					.build();
		}
	}


}
