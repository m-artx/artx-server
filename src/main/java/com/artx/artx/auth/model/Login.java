package com.artx.artx.auth.model;

import com.artx.artx.auth.model.token.AccessToken;
import com.artx.artx.user.type.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class Login {


	@Getter
	public static class Request {
		private String username;
		private String password;
	}

	@Getter
	@Builder
	public static class Response {
		private AccessToken accessToken;
		private UUID userId;
		private UserRole userRole;
	}
}
