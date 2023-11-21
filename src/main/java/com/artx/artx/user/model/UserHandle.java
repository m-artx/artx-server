package com.artx.artx.user.model;

import lombok.Builder;
import lombok.Getter;

public class UserHandle {


	@Getter
	public static class Request {
		private String email;
		private String username;
	}

	@Getter
	@Builder
	public static class UsernameResponse {
		private String username;
	}

	@Getter
	@Builder
	public static class PasswordResponse{
	}
}
