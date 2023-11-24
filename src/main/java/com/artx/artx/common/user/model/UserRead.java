package com.artx.artx.common.user.model;

import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.type.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserRead {

	@Getter
	@Builder
	public static class Response {
		@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;

		@Schema(description = "프로필 이미지", example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String userProfileImage;

		@Schema(description = "소개", example = "그림 그립니다.")
		private String userIntroduction;

		@Schema(description = "아이디", example = "artxlover")
		private String username;

		@Schema(description = "이메일", example = "artx@gmail.com")
		private String userEmail;

		@Schema(description = "닉네임", example = "김작가")
		private String userNickname;

		@Schema(description = "휴대폰 번호", example = "010-1234-5678")
		private String userPhoneNumber;

		@Schema(description = "유저 권한", example = "ROLE_USER")
		private UserRole userRole;

		@Schema(description = "가입 날짜", example = "2023-12-01")
		private LocalDate userCreatedAt;

		public static Response of(User user, String imagesApiAddress) {
			return Response.builder()
					.userId(user.getUserId())
					.userProfileImage(imagesApiAddress + user.getProfileImage())
					.userIntroduction(user.getIntroduction())
					.username(user.getUsername())
					.userEmail(user.getEmail())
					.userNickname(user.getNickname())
					.userPhoneNumber(user.getPhoneNumber())
					.userRole(user.getUserRole())
					.userCreatedAt(user.getCreatedAt().toLocalDate())
					.build();
		}
	}

	@Getter
	@Builder
	public static class ResponseAll{
		@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;

		@Schema(description = "유저 이미지", example = "http://127.0.0.1:8080/api/images/1f66d818-4ff2-4a14-9c0c-d77dc30c0639_Rectangle_635.png")
		private String userProfileImage;

		@Schema(description = "유저 소개", example = "그림 그립니다.")
		private String userIntroduction;

		@Schema(description = "아이디", example = "artxlover")
		private String username;

		@Schema(description = "이메일", example = "artx@gmail.com")
		private String userEmail;

		@Schema(description = "닉네임", example = "김작가")
		private String userNickname;

		@Schema(description = "휴대폰 번호", example = "010-1234-5678")
		private String userPhoneNumber;

		@Schema(description = "주소", example = "서울특별시 은천로 1길 18")
		private String userAddress;

		@Schema(description = "상세 주소", example = "101호")
		private String userAddressDetail;

		@Schema(description = "생성 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime userCreatedAt;

		public static ResponseAll of(User user) {
			return ResponseAll.builder()
					.userId(user.getUserId())
					.userProfileImage(user.getProfileImage())
					.userIntroduction(user.getIntroduction())
					.username(user.getUsername())
					.userEmail(user.getEmail())
					.userNickname(user.getNickname())
					.userPhoneNumber(user.getPhoneNumber())
					.userAddress(user.getDefaultAddress().getAddress().getAddress())
					.userAddressDetail(user.getDefaultAddress().getAddress().getAddressDetail())
					.userCreatedAt(user.getCreatedAt())
					.build();
		}
	}

}
