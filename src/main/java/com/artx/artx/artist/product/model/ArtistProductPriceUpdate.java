package com.artx.artx.artist.product.model;

import lombok.Getter;

public class ArtistProductPriceUpdate {

	@Getter
	public static class Request {
		private Long productPrice;
	}

}
