package com.artx.artx.user.model;

import com.artx.artx.user.entity.User;
import com.artx.artx.user.type.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserCreate {

	@Getter
	public static class Request {
		@Schema(description = "아이디", example = "artxlover")
		private String username;
		@Schema(description = "패스워드", example = "@artx1234@")
		private String password;
		@Schema(description = "이메일", example = "artx@gmail.com")
		private String email;
		@Schema(description = "이메일 수신 동의", example = "false")
		private Boolean isEmailYn;
		@Schema(description = "닉네임", example = "김작가")
		private String nickname;
		@Schema(description = "휴대폰 번호", example = "010-1234-5678")
		private String phoneNumber;
		@Schema(description = "주소", example = "서울특별시 은천로 1길")
		private String address;
		@Schema(description = "상세 주소", example = "101호")
		private String addressDetail;
		@Schema(description = "회원", example = "USER")
		private UserRole userRole;
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
