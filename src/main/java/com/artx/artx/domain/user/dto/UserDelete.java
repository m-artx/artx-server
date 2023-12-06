package com.artx.artx.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserDelete {

	@Getter
	public static class Request {

		@Schema(description = "패스워드", example = "@artx1234@")
		@NotBlank
		public String password;
	}

	@Getter
	@Builder
	public static class Response {
		public LocalDateTime userDeletedAt;
	}

}
