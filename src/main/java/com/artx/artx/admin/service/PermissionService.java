package com.artx.artx.admin.service;

import com.artx.artx.admin.entity.PermissionRequest;
import com.artx.artx.admin.model.permission.ReadPermissionRequest;
import com.artx.artx.admin.model.permission.UpdatePermissionRequest;
import com.artx.artx.admin.repository.PermissionRequestRepository;
import com.artx.artx.admin.type.PermissionRequestStatus;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.model.permission.CreateUserPermissionRequest;
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
public class PermissionService {

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value(value = "${api.permissions}")
	private String permissionsApiAddress;

	private final PermissionRequestRepository permissionRequestRepository;
	private final ImageService imageService;

	@Transactional
	public void createPermissionRequest(User user, CreateUserPermissionRequest.Request request, List<MultipartFile> images){
		PermissionRequest permissionRequest = PermissionRequest.from(request);
		permissionRequest.setFirstImage(images.get(0).getName());
		permissionRequest.setSecondImage(images.get(1).getName());
		permissionRequest.setUser(user);
		permissionRequest.setPreviousRole(user.getUserRole());
		permissionRequest.setRequestedRole(request.getRole());

		permissionRequestRepository.save(permissionRequest);
	}

	@Transactional
	public UpdatePermissionRequest.Response updatePermission(Long permissionRequestId, PermissionRequestStatus permissionRequestStatus) {

		PermissionRequest permissionRequest = permissionRequestRepository.findById(permissionRequestId)
				.orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_REQUEST_NOT_FOUND));
		permissionRequest.setStatus(permissionRequestStatus);

		if(permissionRequestStatus == PermissionRequestStatus.APPROVAL){
			User user = permissionRequest.getUser();
			user.setUserRole(permissionRequest.requestedRole);
		}

		return UpdatePermissionRequest.Response.builder().updatedAt(LocalDateTime.now()).build();
	}

	@Transactional(readOnly = true)
	public Page<PermissionRequest> getPermissionRequestPagesByUserId(UUID userId, Pageable pageable){
		return permissionRequestRepository.findPermissionRequestByUser_UserId(userId, pageable);
	}

	@Transactional(readOnly = true)
	public Page<ReadPermissionRequest.ResponseAll> readAllPermissionRequest(PermissionRequestStatus permissionRequestStatus, Pageable pageable) {
		return permissionRequestRepository.findAllPermissionRequestPageByStatus(permissionRequestStatus, pageable)
				.map(permissionRequest -> ReadPermissionRequest.ResponseAll.from(permissionsApiAddress, permissionRequest));
	}

	public ReadPermissionRequest.Response readPermissionRequest(Long permissionRequestId) {
		PermissionRequest permissionRequest = permissionRequestRepository.findById(permissionRequestId).orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_REQUEST_NOT_FOUND));
		return ReadPermissionRequest.Response.from(imagesApiAddress, permissionRequest);
	}

}
