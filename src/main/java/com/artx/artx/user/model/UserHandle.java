package com.artx.artx.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class UserHandle {


	@Getter
	public static class Request {
		@Schema(description = "아이디", example = "artxlover")
		private String username;

		@Schema(description = "이메일", example = "artx@gmail.com")
		private String email;

	}

	@Getter
	@Builder
	public static class UsernameResponse {
		@Schema(description = "아이디", example = "artxlover")
		private String username;
	}

}
