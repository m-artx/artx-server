package com.artx.artx.admin.model.permission;

import com.artx.artx.admin.entity.PermissionRequest;
import com.artx.artx.admin.type.PermissionRequestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PermissionRequestRead {

	@Getter
	@Builder
	public static class Response {
		public String permissionRequestTitle;
		public String permissionRequestContent;
		public String permissionRequestFirstImage;
		public String permissionRequestSecondImage;
		public PermissionRequestStatus permissionRequestStatus;
		public LocalDateTime permissionRequestCreatedAt;

		public static Response of(String imagesApiAddress, PermissionRequest permissionRequest) {
			return Response.builder()
					.permissionRequestTitle(permissionRequest.getTitle())
					.permissionRequestContent(permissionRequest.getContent())
					.permissionRequestFirstImage(imagesApiAddress + permissionRequest.getFirstImage())
					.permissionRequestSecondImage(imagesApiAddress + permissionRequest.getSecondImage())
					.permissionRequestStatus(permissionRequest.getStatus())
					.permissionRequestCreatedAt(permissionRequest.getCreatedAt())
					.build();
		}
	}

	@Getter
	@Builder
	public static class ResponseAll {
		public String permissionRequestLink;
		public String permissionRequestTitle;
		public String permissionRequestContent;
		public PermissionRequestStatus permissionRequestStatus;
		public LocalDateTime permissionRequestCreatedAt;

		public static ResponseAll of(String permissionsApiAddress, PermissionRequest permissionRequest) {
			return ResponseAll.builder()
					.permissionRequestLink(permissionsApiAddress + permissionRequest.getId())
					.permissionRequestTitle(permissionRequest.getTitle())
					.permissionRequestContent(permissionRequest.getContent())
					.permissionRequestStatus(permissionRequest.getStatus())
					.permissionRequestCreatedAt(permissionRequest.getCreatedAt())
					.build();
		}
	}

}
