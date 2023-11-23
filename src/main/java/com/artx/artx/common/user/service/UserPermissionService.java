package com.artx.artx.common.user.service;

import com.artx.artx.admin.permission.service.AdminPermissionService;
import com.artx.artx.common.user.model.permission.UserPermissionRequestCreate;
import com.artx.artx.common.user.model.permission.UserPermissionRequestRead;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.common.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPermissionService {

	private final ImageService imageService;
	private final UserService userService;
	private final AdminPermissionService permissionService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public UserPermissionRequestCreate.Response createRequestPermission(UUID userId, UserPermissionRequestCreate.Request request) {
		isValidFileSize(2, request.getPermissionImages());
		User user = userService.getUserByUserId(userId);
		isAlreadySamePermission(request, user);

		permissionService.createPermissionRequest(user, request);
		return UserPermissionRequestCreate.Response.of(LocalDateTime.now());
	}
	@Transactional(readOnly = true)
	public Page<UserPermissionRequestRead.Response> readRequestPermission(UUID userId, Pageable pageable) {
		return permissionService.getPermissionRequestPagesByUserId(userId, pageable).map(UserPermissionRequestRead.Response::from);
	}

	public String setPermissionImage(MultipartFile file) {
		return imagesApiAddress + imageService.savePermissionImage(file);
	}

	private boolean isAlreadySamePermission(UserPermissionRequestCreate.Request request, User user) {
		if (user.getUserRole() == request.getRole()) {
			throw new BusinessException(ErrorCode.ALREADY_SAME_PERMISSION);
		}
		return true;
	}

	private boolean isValidFileSize(int size, List<String> images) {
		if (images.size() != size) {
			throw new BusinessException(ErrorCode.NEED_AT_TWO_FILES);
		}
		return true;
	}

}
