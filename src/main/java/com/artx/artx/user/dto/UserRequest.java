package com.artx.artx.user.dto;

import com.artx.artx.user.type.UserRole;
import lombok.Getter;

public class UserRequest {

	@Getter
	public static class Create{
		private String username;
		private String password;
		private String email;
		private Boolean isEmailYn;
		private String nickname;
		private String phoneNumber;
		private String address;
		private String addressDetail;
		private UserRole userRole;
	}

}
