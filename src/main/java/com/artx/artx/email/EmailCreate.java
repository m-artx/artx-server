package com.artx.artx.email;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class EmailCreate {

	@Getter
	@Builder
	public static class JoinRequest {
		private String to;
		private UUID userId;
	}

	@Getter
	@Builder
	public static class PasswordRequest {
		private String to;
		private String newPassword;
	}



	@Getter
	public static class Response {
		private UUID userId;
	}

}
