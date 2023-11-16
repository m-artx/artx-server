package com.artx.artx.admin.model.permission;

import com.artx.artx.admin.type.PermissionRequestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UpdatePermissionRequest {

	@Getter
	public static class Request {
		private PermissionRequestStatus status;
	}

	@Getter
	@Builder
	public static class Response {
		private LocalDateTime updatedAt;
	}

}
