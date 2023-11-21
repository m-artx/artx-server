package com.artx.artx.user.controller;

import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.user.model.permission.UserPermissionRequestCreate;
import com.artx.artx.user.model.permission.UserPermissionRequestRead;
import com.artx.artx.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Tag(name = "유저 권한")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permission-request")
public class UserPermissionController {

	private final UserService userService;

	@Operation(summary = "권한 변경 신청", description = "권한 변경을 신청할 수 있다.")
	@PostMapping
	public ResponseEntity<UserPermissionRequestCreate.Response> createRequestPermission(
			@RequestPart List<MultipartFile> files,
			@RequestPart UserPermissionRequestCreate.Request request
	){
		UserPermissionRequestCreate.Response response = userService.requestPermission(getUserId(), request, files);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "권한 변경 신청 조회", description = "권한 변경을 신청할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<UserPermissionRequestRead.Response>> readRequestPermission(
			Pageable pageable
	){
		return ResponseEntity.ok(userService.readRequestPermission(getUserId(), pageable));
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
