package com.artx.artx.common.auth.model;

import com.artx.artx.common.auth.model.token.AccessToken;
import lombok.Builder;
import lombok.Getter;

public class Logout {

	@Getter
	@Builder
	public static class Response {
		private AccessToken accessToken;
	}
}
