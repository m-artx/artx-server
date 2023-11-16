package com.artx.artx.user.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class DeleteUser {

	@Getter
	public static class Request {
		public String password;
	}

	@Getter
	@Builder
	public static class Response {
		public LocalDateTime deletedAt;
	}

}
