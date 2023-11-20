package com.artx.artx.artist.model;

import lombok.Getter;
import java.util.List;

public class ArtistProductDelete {

	@Getter
	public static class Request {
		private List<Long> productIds;
	}

}
