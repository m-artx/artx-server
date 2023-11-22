package com.artx.artx.common.email;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class EmailCreate {

	@Getter
	@Builder
	public static class Request {
		private String to;
		private UUID userId;
	}

	@Getter
	public static class Response {
		private UUID userId;
	}

}
