package com.artx.artx.domain.auth.dto;

import com.artx.artx.domain.user.domain.UserRole;
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
