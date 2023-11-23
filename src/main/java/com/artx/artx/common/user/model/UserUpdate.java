package com.artx.artx.common.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserUpdate {

	@Getter
	public static class Request {
		@Schema(description = "이전 패스워드", example = "@artx1234@")
		@NotBlank
		public String previousPassword;

		@Schema(description = "새로운 패스워드", example = "@artx1234@")
		@NotBlank
		public String presentPassword;
	}

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "유저 수정 시간", example = "2023-01-01T10:00:30")
		public LocalDateTime updatedAt;
	}

}
