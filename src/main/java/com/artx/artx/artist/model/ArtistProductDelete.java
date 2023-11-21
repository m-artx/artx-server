package com.artx.artx.artist.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.util.List;

public class ArtistProductDelete {

	@Getter
	public static class Request {
		@NotNull
		@Valid
		private List<Long> productIds;
	}

}
