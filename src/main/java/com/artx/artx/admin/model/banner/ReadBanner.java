package com.artx.artx.admin.model.banner;

import com.artx.artx.admin.entity.Banner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


public class ReadBanner {

	@Builder
	@Getter
	public static class Response {
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
		@Schema(description = "작품 대표 이미지", nullable = false, example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String productRepresentativeImage;
		@Schema(description = "작품 전체 이미지", nullable = false, example = "[http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png, ...]")
		private List<String> productImages;
		@Schema(description = "작품 링크", nullable = false, example = "http://127.0.0.1:8080/api/products/1")
		private String productLink;

		public static Response from(Banner banner){
			return Response.builder()
					.productId(banner.getProduct().getId())
					.productRepresentativeImage(banner.getProduct().getRepresentativeImage())
					.productLink(banner.getLink())
					.build();
		}
	}




}
