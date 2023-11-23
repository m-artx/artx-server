package com.artx.artx.common.user.model;

import com.artx.artx.common.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class UserEmail {


	@Getter
	public static class UsernameRequest {

		@Schema(description = "이메일", example = "artx@gmail.com")
		private String email;

	}

	@Getter
	public static class PasswordRequest {
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

		public static UserEmail.UsernameResponse from(User user) {
			return UserEmail.UsernameResponse
					.builder()
					.username(user.getUsername())
					.build();
		}
	}

}
