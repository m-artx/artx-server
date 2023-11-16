package com.artx.artx.user.model;

import com.artx.artx.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class ReadUser {

	@Getter
	@Builder
	public static class Response {

		@Schema(description = "유저 고유 식별 번호", nullable = false, example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "유저 이미지", nullable = false, example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String userProfileImage;
		@Schema(description = "유저 소개", nullable = false, example = "그림 그립니다.")
		private String userIntroduction;
		@Schema(description = "아이디", nullable = false, example = "artxlover")
		private String username;
		@Schema(description = "이메일", nullable = false, example = "artx@gmail.com")
		private String userEmail;
		@Schema(description = "닉네임", nullable = false, example = "김작가")
		private String userNickname;
		@Schema(description = "휴대폰 번호", nullable = false, example = "010-1234-5678")
		private String userPhoneNumber;

		public static Response from(User user) {
			return Response.builder()
					.userId(user.getUserId())
					.userProfileImage(user.getProfileImage())
					.userIntroduction(user.getIntroduction())
					.username(user.getUsername())
					.userEmail(user.getEmail())
					.userNickname(user.getNickname())
					.userPhoneNumber(user.getPhoneNumber())
					.build();
		}
	}

}
