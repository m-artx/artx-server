package com.artx.artx.common.user.controller;

import com.artx.artx.common.user.model.*;
import com.artx.artx.common.user.service.UserService;
import com.artx.artx.common.user.type.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "가입 정보와 함께 회원가입을 할 수 있다.")
	@PostMapping
	public ResponseEntity<UserCreate.Response> createUser(
			@Valid @RequestBody UserCreate.Request request
	) {
		UserCreate.Response response = userService.createUser(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "회원탈퇴", description = "회원 탈퇴를 할 수 있다.")
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserDelete.Response> delete(
			@PathVariable UUID userId,
			@Valid @RequestBody UserDelete.Request request
	) {
		UserDelete.Response response = userService.deleteUser(userId, request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이디 찾기", description = "이메일 계정을 통해 등록된 아이디를 찾는다.")
	@PostMapping("/find-username")
	public ResponseEntity<UserEmail.UsernameResponse> findUsername(
			@RequestBody UserEmail.UsernameRequest request
	) {
		return ResponseEntity.ok(userService.findUsernameByEmail(request.getEmail()));
	}

	@Operation(summary = "패스워드 초기화", description = "이메일 전송을 통해 새로운 패스워드를 발급받는다.")
	@PostMapping("/init-password")
	public ResponseEntity<Void> initPassword(
			@RequestBody UserEmail.PasswordRequest request
	) {
		userService.resetPassword(request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "특정 유저 프로필 조회", description = "특정 유저를 조회할 수 있다.")
	@GetMapping("/{userId}")
	public ResponseEntity<UserRead.Response> readUser(
			@PathVariable UUID userId
	) {
		UserRead.Response response = userService.readUser(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "권한별 유저 조회", description = "유저들을 권한과 함께 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<UserReadDto>> readNewUsers(
			@RequestParam UserRole type,
			Pageable pageable
	) {
		return ResponseEntity.ok(userService.readUsersByUserRole(type, pageable));
	}

	@Operation(summary = "이메일 중복 확인", description = "이메일 중복을 확인할 수 있다.")
	@PostMapping("/validation/email")
	public ResponseEntity<Void> isValidEmail(
			@RequestBody UserValid.Email request
	) {
		userService.isValidEmail(request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "아이디 중복 확인", description = "아이디 중복을 확인할 수 있다.")
	@PostMapping("/validation/username")
	public ResponseEntity<Void> isValidEmail(
			@RequestBody UserValid.Username request
	) {
		userService.isValidUsername(request);
		return ResponseEntity.ok().build();
	}

}
