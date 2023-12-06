package com.artx.artx.domain.artist.product.dto;

import lombok.Getter;

public class ArtistProductQuantityUpdate {

	@Getter
	public static class Request {
		private Long productQuantity;
	}

}
