package com.artx.artx.common.user.model;

import lombok.Builder;
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
