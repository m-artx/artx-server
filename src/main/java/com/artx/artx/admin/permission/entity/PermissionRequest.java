package com.artx.artx.admin.permission.entity;

import com.artx.artx.admin.permission.type.PermissionRequestStatus;
import com.artx.artx.etc.model.BaseEntity;
import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.model.permission.UserPermissionRequestCreate;
import com.artx.artx.common.user.type.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	public User user;

	@Enumerated(EnumType.STRING)
	public UserRole previousRole;

	@Enumerated(EnumType.STRING)
	public UserRole requestedRole;

	private String title;
	private String content;
	private String firstImage;
	private String secondImage;

	@Enumerated(EnumType.STRING)
	private PermissionRequestStatus status;

	public static PermissionRequest from(UserPermissionRequestCreate.Request request) {
		return PermissionRequest.builder()
				.title(request.getTitle())
				.content(request.getContent())
				.status(PermissionRequestStatus.PROPOSAL)
				.build();
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public void setSecondImage(String secondImage) {
		this.secondImage = secondImage;
	}

	public void setStatus(PermissionRequestStatus status) {
		this.status = status;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPreviousRole(UserRole userRole) {
		this.previousRole = userRole;
	}

	public void setRequestedRole(UserRole userRole) {
		this.requestedRole = userRole;
	}

	public void update(UserPermissionRequestCreate.Request request) {
		this.title = request.getTitle();
		this.content = request.getContent();
		this.status = PermissionRequestStatus.PROPOSAL;
	}
}
