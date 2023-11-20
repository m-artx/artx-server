package com.artx.artx.user.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserUpdate {

	@Getter
	public static class Request {
		public String previousPassword;
		public String presentPassword;
	}

	@Getter
	@Builder
	public static class Response {
		public LocalDateTime updatedAt;
	}

}
