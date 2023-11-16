package com.artx.artx.user.model.permission;

import com.artx.artx.user.type.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CreateUserPermissionRequest {

		@Getter
		public static class Request {
			public UserRole role;
			public String title;
			public String content;
		}

		@Getter
		@Builder
		public static class Response {
			public LocalDateTime createdAt;
		}

}
