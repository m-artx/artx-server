package com.artx.artx.common.user.model;

import com.artx.artx.common.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserCreate {

	@Getter
	public static class Request {

		@Schema(description = "아이디", example = "artxlover")
		@NotBlank
		private String username;

		@Schema(description = "패스워드", example = "@artx1234@")
		@NotBlank
		private String password;

		@Schema(description = "이메일", example = "artx@gmail.com")
		@NotBlank
		@Email
		private String email;

		@Schema(description = "이메일 수신 동의", example = "false")
		@NotNull
		private Boolean isEmailYn;

		@Schema(description = "닉네임", example = "김작가")
		@NotBlank
		private String nickname;

		@Schema(description = "휴대폰 번호", example = "010-1234-5678")
		@NotBlank
		private String phoneNumber;

		@Schema(description = "주소", example = "서울특별시 은천로 1길")
		@NotBlank
		private String address;

		@Schema(description = "상세 주소", example = "101호")
		@NotBlank
		private String addressDetail;
	}

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "유저 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime userCreatedAt;

		public static Response of(User user) {
			return Response.builder()
					.userId(user.getUserId())
					.userCreatedAt(user.getCreatedAt())
					.build();
		}
	}

}
