package com.artx.artx.banner.model;

import com.artx.artx.banner.entity.Banner;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BannerResponse {

	private Long productId;
	private String representativeImageUrl;
	private String link;

	public static BannerResponse from(Banner banner){
		return BannerResponse.builder()
				.productId(banner.getProduct().getId())
				.representativeImageUrl(banner.getProduct().getRepresentativeImage())
				.link(banner.getLink())
				.build();
	}

}
