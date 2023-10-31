package com.artx.artx.user.dto;

import com.artx.artx.user.model.User;
import com.artx.artx.user.type.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class UserResponse {

	@Getter
	@Builder
	public static class Create {
		private UUID userId;
		private String nickname;

		public static UserResponse.Create from(User user) {
			return Create.builder()
					.userId(user.getUserId())
					.nickname(user.getNickname())
					.build();
		}
	}

	@Getter
	@Builder
	public static class Read {

		private UserRole userRole;
		private UUID userId;
		private String username;
		private String email;
		private Boolean isEmailYn;
		private String nickname;
		private String phoneNumber;
		private String address;
		private String addressDetail;

		public static UserResponse.Read from(User user) {
			return Read.builder()
					.userId(user.getUserId())
					.userRole(user.getUserRole())
					.username(user.getUsername())
					.email(user.getEmail())
					.isEmailYn(user.getIsEmailYn())
					.nickname(user.getNickname())
					.phoneNumber(user.getPhoneNumber())
					.address(user.getAddress().getAddress())
					.addressDetail(user.getAddress().getAddressDetail())
					.build();
		}
	}

}
