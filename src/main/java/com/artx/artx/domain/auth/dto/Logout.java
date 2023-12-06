package com.artx.artx.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

public class Logout {

	@Getter
	@Builder
	public static class Response {
		private AccessToken accessToken;
	}
}
