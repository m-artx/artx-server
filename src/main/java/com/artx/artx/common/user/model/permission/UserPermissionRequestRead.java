package com.artx.artx.common.user.model.permission;

import com.artx.artx.admin.permission.entity.PermissionRequest;
import com.artx.artx.admin.permission.type.PermissionRequestStatus;
import lombok.Builder;
import lombok.Getter;

public class UserPermissionRequestRead {

	@Getter
	@Builder
	public static class Response {
		public String permissionRequestTitle;
		public String permissionRequestContent;
		public PermissionRequestStatus permissionRequestStatus;

		public static Response from(PermissionRequest permissionRequest) {

			return Response.builder()
					.permissionRequestTitle(permissionRequest.getTitle())
					.permissionRequestContent(permissionRequest.getContent())
					.permissionRequestStatus(permissionRequest.getStatus())
					.build();
		}
	}

}
