package com.artx.artx.user.model;

import com.artx.artx.user.entity.User;
import com.artx.artx.user.type.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateUser {

	@Getter
	public static class Request {
		@Schema(description = "아이디", nullable = false, example = "artxlover")
		private String username;
		@Schema(description = "패스워드", nullable = false, example = "@artx1234@")
		private String password;
		@Schema(description = "이메일", nullable = false, example = "artx@gmail.com")
		private String email;
		@Schema(description = "이메일 수신 동의", nullable = false, example = "false")
		private Boolean isEmailYn;
		@Schema(description = "닉네임", nullable = false, example = "김작가")
		private String nickname;
		@Schema(description = "휴대폰 번호", nullable = false, example = "010-1234-5678")
		private String phoneNumber;
		@Schema(description = "주소", nullable = false, example = "서울특별시 은천로 1길")
		private String address;
		@Schema(description = "상세 주소", nullable = false, example = "101호")
		private String addressDetail;
		@Schema(description = "회원", nullable = false, example = "USER")
		private UserRole userRole;
	}

	@Getter
	@Builder
	public static class Response {

		@Schema(description = "유저 고유 식별 번호", nullable = false, example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "유저 등록 시간", nullable = false, example = "2023-01-01T10:00:30")
		private LocalDateTime createdAt;

		public static Response from(User user) {
			return Response.builder()
					.userId(user.getUserId())
					.createdAt(user.getCreatedAt())
					.build();
		}
	}

}
