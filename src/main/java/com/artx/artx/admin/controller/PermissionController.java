package com.artx.artx.admin.controller;

import com.artx.artx.admin.model.permission.ReadPermissionRequest;
import com.artx.artx.admin.model.permission.UpdatePermissionRequest;
import com.artx.artx.admin.service.PermissionService;
import com.artx.artx.admin.type.PermissionRequestStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin/permission-requests")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class PermissionController {

	private final PermissionService permissionService;

	@GetMapping("/{permissionRequestId}")
	public ResponseEntity<ReadPermissionRequest.Response> readPemissionRequest(
			@PathVariable Long permissionRequestId
	){
		return ResponseEntity.ok(permissionService.readPermissionRequest(permissionRequestId));
	}

	@GetMapping
	public ResponseEntity<Page<ReadPermissionRequest.ResponseAll>> readAllPemissionRequest(
			@RequestParam PermissionRequestStatus status,
			Pageable pageable
	){
		return ResponseEntity.ok(permissionService.readAllPermissionRequest(status, pageable));
	}

	@PatchMapping("/{permissionRequestId}")
	public ResponseEntity<UpdatePermissionRequest.Response> updatePemissionRequest(
			@PathVariable Long permissionRequestId,
			@RequestParam PermissionRequestStatus status
	){
		return ResponseEntity.ok(permissionService.updatePermission(permissionRequestId, status));
	}

}

