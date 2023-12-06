package com.artx.artx.domain.artist.product.dto;

import lombok.Getter;

public class ArtistProductPriceUpdate {

	@Getter
	public static class Request {
		private Long productPrice;
	}

}
