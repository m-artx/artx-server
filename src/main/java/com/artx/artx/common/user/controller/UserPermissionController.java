package com.artx.artx.common.user.controller;

import com.artx.artx.common.auth.model.UserDetails;
import com.artx.artx.common.user.service.UserPermissionService;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.common.user.model.permission.UserPermissionRequestCreate;
import com.artx.artx.common.user.model.permission.UserPermissionRequestRead;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Tag(name = "유저 권한")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permission-request")
public class UserPermissionController {

	private final UserPermissionService userPermissionService;

	@Operation(summary = "권한 신청 이미지 등록", description = "권한 신청 시 이미지를 등록할 수 있다.")
	@PostMapping("/image")
	public ResponseEntity<String> addProfileImage(
			@RequestParam MultipartFile file){
		return ResponseEntity.ok(userPermissionService.setPermissionImage(file));
	}

	@Operation(summary = "권한 변경 신청", description = "권한 변경을 신청할 수 있다.")
	@PostMapping
	public ResponseEntity<UserPermissionRequestCreate.Response> createRequestPermission(
			@RequestBody UserPermissionRequestCreate.Request request
	){
		UserPermissionRequestCreate.Response response = userPermissionService.createRequestPermission(getUserId(), request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "권한 변경 신청 조회", description = "권한 변경을 신청할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<UserPermissionRequestRead.Response>> readRequestPermission(
			Pageable pageable
	){
		return ResponseEntity.ok(userPermissionService.readRequestPermission(getUserId(), pageable));
	}

	public UUID getUserId() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userDetails.getUserId();

		} catch (ClassCastException e) {
			throw new BusinessException(ErrorCode.NEED_TO_CHECK_TOKEN);
		}
	}

}
