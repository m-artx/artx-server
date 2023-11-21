package com.artx.artx.user.model;

import lombok.Builder;
import lombok.Getter;

public class UserHandle {

	@Getter
	@Builder
	public static class Username{
		private String username;
	}

	@Getter
	@Builder
	public static class Password{
	}
}
