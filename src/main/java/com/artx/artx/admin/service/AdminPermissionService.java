package com.artx.artx.admin.service;

import com.artx.artx.admin.entity.PermissionRequest;
import com.artx.artx.admin.model.permission.PermissionRequestRead;
import com.artx.artx.admin.model.permission.PermissionRequestUpdate;
import com.artx.artx.admin.repository.PermissionRequestRepository;
import com.artx.artx.admin.type.PermissionRequestStatus;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.model.permission.UserPermissionRequestCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminPermissionService {

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value(value = "${api.permissions}")
	private String permissionsApiAddress;

	private final PermissionRequestRepository permissionRequestRepository;
	private final ImageService imageService;

	@Transactional
	public void createPermissionRequest(User user, UserPermissionRequestCreate.Request request){
		PermissionRequest permissionRequest;
		if(permissionRequestRepository.existsByUser_UserId(user.getUserId())){
			permissionRequest = permissionRequestRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_REQUEST_NOT_FOUND));
			permissionRequest.update(request);
		} else {
			permissionRequest = PermissionRequest.from(request);
		}

		permissionRequest.setFirstImage(request.getPermissionImages().get(0));
		permissionRequest.setSecondImage(request.getPermissionImages().get(0));
		permissionRequest.setUser(user);
		permissionRequest.setPreviousRole(user.getUserRole());
		permissionRequest.setRequestedRole(request.getRole());

		permissionRequestRepository.save(permissionRequest);
	}

	@Transactional
	public PermissionRequestUpdate.Response updatePermission(Long permissionRequestId, PermissionRequestStatus permissionRequestStatus) {

		PermissionRequest permissionRequest = permissionRequestRepository.findById(permissionRequestId)
				.orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_REQUEST_NOT_FOUND));
		permissionRequest.setStatus(permissionRequestStatus);

		if(permissionRequestStatus == PermissionRequestStatus.APPROVAL){
			User user = permissionRequest.getUser();
			user.setUserRole(permissionRequest.requestedRole);

			imageService.removeImage(permissionRequest.getFirstImage());
			imageService.removeImage(permissionRequest.getSecondImage());
		}

		return PermissionRequestUpdate.Response.builder().updatedAt(LocalDateTime.now()).build();
	}

	@Transactional(readOnly = true)
	public Page<PermissionRequest> getPermissionRequestPagesByUserId(UUID userId, Pageable pageable){
		return permissionRequestRepository.findPermissionRequestByUser_UserId(userId, pageable);
	}

	@Transactional(readOnly = true)
	public Page<PermissionRequestRead.ResponseAll> readAllPermissionRequest(PermissionRequestStatus permissionRequestStatus, Pageable pageable) {
		return permissionRequestRepository.findAllPermissionRequestPageByStatus(permissionRequestStatus, pageable)
				.map(permissionRequest -> PermissionRequestRead.ResponseAll.of(permissionsApiAddress, permissionRequest));
	}

	public PermissionRequestRead.Response readPermissionRequest(Long permissionRequestId) {
		PermissionRequest permissionRequest = permissionRequestRepository.findById(permissionRequestId).orElseThrow(() -> new BusinessException(ErrorCode.PERMISSION_REQUEST_NOT_FOUND));
		return PermissionRequestRead.Response.of(imagesApiAddress, permissionRequest);
	}

}
