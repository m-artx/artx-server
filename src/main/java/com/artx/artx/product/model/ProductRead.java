package com.artx.artx.product.model;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductImage;
import com.artx.artx.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductRead {

	@Getter
	@Builder
	public static class DetailResponse {
		@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;

		@Schema(description = "유저 이미지", example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String userProfileImage;

		@Schema(description = "유저 소개", example = "그림 그립니다.")
		private String userIntroduction;

		private ProductRead.SummaryResponse productReadSummaryResponse;

		@Schema(description = "작품 전체 이미지", example = "[http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png, ...]")
		private List<String> productImages;

		@Schema(description = "작품 설명", example = "어둑한 밤을 화폭에 옮겨 담았습니다.")
		private String productDescription;


		public static DetailResponse of(String imagesApiAddress, String productsApiAddress, Product product, User user) {
			List<String> fileImageNames = product.getProductImages().stream().map(ProductImage::getName).collect(Collectors.toList());
			List<String> fileImageUrls = new ArrayList<>();

			for (int i = 0; i < fileImageNames.size(); i++) {
				fileImageUrls.add(imagesApiAddress + fileImageNames.get(i));
			}

			return DetailResponse.builder()
					.userId(user.getUserId())
					.userIntroduction(user.getIntroduction())
					.userProfileImage(imagesApiAddress + user.getProfileImage())
					.productReadSummaryResponse(SummaryResponse.of(imagesApiAddress, productsApiAddress, product))
					.productImages(fileImageUrls)
					.productDescription(product.getDescription())
					.build();
		}
	}

	@Getter
	@Builder
	public static class SummaryResponse {
		@Schema(description = "작품 고유 식별 번호", example = "1")
		private Long productId;

		@Schema(description = "작품 링크", example = "http://127.0.0.1:8080/api/products/1")
		private String productLink;

		@Schema(description = "작품 대표 이미지", example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productRepresentativeImage;

		@Schema(description = "작품명", example = "검은 장미")
		private String productTitle;

		@Schema(description = "작품 재고 수량", example = "100")
		private Long productStockQuantity;

		@Schema(description = "작품 가격", example = "100000")
		private Long productPrice;

		@Schema(description = "작품 등록 날짜", example = "2023-01-01T10:00:30")
		private LocalDateTime productCreatedAt;

		public static SummaryResponse of(String imageApiAddress, String productApiAddress, Product product) {
			return SummaryResponse.builder()
					.productId(product.getId())
					.productLink(productApiAddress + product.getId())
					.productRepresentativeImage(imageApiAddress + product.getRepresentativeImage())
					.productTitle(product.getTitle())
					.productPrice(product.getPrice())
					.productStockQuantity(product.getProductStock().getQuantity())
					.productCreatedAt(product.getCreatedAt())
					.build();
		}
	}

}
