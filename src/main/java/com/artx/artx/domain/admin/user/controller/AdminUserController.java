package com.artx.artx.domain.admin.user.controller;

import com.artx.artx.domain.admin.user.service.AdminUserService;
import com.artx.artx.domain.user.dto.UserRead;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminUserController {

	private final AdminUserService adminUserService;

	@Operation(summary = "모든 유저 조회", description = "특정 유저를 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<UserRead.ResponseAll>> readAllUsers(Pageable pageable){
		return ResponseEntity.ok(adminUserService.readAllUsers(pageable));
	}

}
