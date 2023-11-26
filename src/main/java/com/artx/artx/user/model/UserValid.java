package com.artx.artx.user.model;

import lombok.Getter;

public class UserValid {

	@Getter
	public static class Username {
		private String username;
	}

	@Getter
	public static class Email {
		private String email;
	}

}
