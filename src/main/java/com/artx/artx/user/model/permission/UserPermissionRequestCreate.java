package com.artx.artx.user.model.permission;

import com.artx.artx.user.type.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserPermissionRequestCreate {

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

			public static Response of(LocalDateTime localDateTime){
				return UserPermissionRequestCreate.
						Response.builder()
						.createdAt(localDateTime)
						.build();
			}
		}

}
