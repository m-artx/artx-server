package com.artx.artx.auth.model;

import com.artx.artx.auth.model.token.AccessToken;
import lombok.Builder;
import lombok.Getter;

public class Logout {

	@Getter
	@Builder
	public static class Response {
		private AccessToken accessToken;
	}
}
