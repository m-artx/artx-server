package com.artx.artx.domain.admin.permission.model;

import com.artx.artx.domain.admin.permission.entity.PermissionRequest;
import com.artx.artx.domain.admin.permission.type.PermissionRequestStatus;
import com.artx.artx.domain.user.domain.UserRole;
import com.artx.artx.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PermissionRequestRead {

	@Getter
	@Builder
	public static class Response {
		public UUID userId;
		public UserRole previousRole;
		public UserRole requestedRole;
		public String username;
		public String nickname;
		public String permissionRequestTitle;
		public String permissionRequestContent;
		public String permissionRequestFirstImage;
		public String permissionRequestSecondImage;
		public PermissionRequestStatus permissionRequestStatus;
		public LocalDateTime permissionRequestCreatedAt;

		public static Response of(String imagesApiAddress, PermissionRequest permissionRequest) {
			User user = permissionRequest.getUser();
			return Response.builder()
					.userId(Objects.isNull(user) ? UUID.fromString("") : user.getUserId())
					.username(Objects.isNull(user) ? "" : user.getUsername())
					.nickname(Objects.isNull(user.getNickname()) ? "" : user.getNickname())
					.previousRole(permissionRequest.getPreviousRole())
					.requestedRole(permissionRequest.getRequestedRole())
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

		public UUID userId;
		public UserRole previousRole;
		public UserRole requestedRole;
		public String username;
		public String nickname;
		public String permissionRequestLink;
		public String permissionRequestTitle;
		public String permissionRequestContent;
		public PermissionRequestStatus permissionRequestStatus;
		public LocalDateTime permissionRequestCreatedAt;

		public static ResponseAll of(String permissionsApiAddress, PermissionRequest permissionRequest) {
			User user = permissionRequest.getUser();
			return ResponseAll.builder()
					.userId(user.getUserId())
					.username(user.getUsername())
					.nickname(user.getNickname())
					.previousRole(permissionRequest.getPreviousRole())
					.requestedRole(permissionRequest.getRequestedRole())
					.permissionRequestLink(permissionsApiAddress + permissionRequest.getId())
					.permissionRequestTitle(permissionRequest.getTitle())
					.permissionRequestContent(permissionRequest.getContent())
					.permissionRequestStatus(permissionRequest.getStatus())
					.permissionRequestCreatedAt(permissionRequest.getCreatedAt())
					.build();
		}
	}

}
