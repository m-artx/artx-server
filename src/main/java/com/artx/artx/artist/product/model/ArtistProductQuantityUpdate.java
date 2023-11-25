package com.artx.artx.artist.product.model;

import lombok.Getter;

public class ArtistProductQuantityUpdate {

	@Getter
	public static class Request {
		private Long productQuantity;
	}

}
