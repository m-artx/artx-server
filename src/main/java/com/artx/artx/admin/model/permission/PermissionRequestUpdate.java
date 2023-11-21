package com.artx.artx.admin.model.permission;

import com.artx.artx.admin.type.PermissionRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PermissionRequestUpdate {

	@Getter
	public static class Request {
		@NotNull
		private PermissionRequestStatus status;
	}

	@Getter
	@Builder
	public static class Response {
		private LocalDateTime updatedAt;
	}

}
