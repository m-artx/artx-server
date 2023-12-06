package com.artx.artx.domain.admin.permission.model;

import com.artx.artx.domain.admin.permission.type.PermissionRequestStatus;
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
