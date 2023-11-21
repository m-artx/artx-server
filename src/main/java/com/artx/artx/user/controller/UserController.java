package com.artx.artx.user.controller;

import com.artx.artx.user.model.UserCreate;
import com.artx.artx.user.model.UserDelete;
import com.artx.artx.user.model.UserRead;
import com.artx.artx.user.model.UserReadDto;
import com.artx.artx.user.service.UserService;
import com.artx.artx.user.type.UserRole;
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
	){
		UserCreate.Response response = userService.createUser(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "특정 유저 프로필 조회", description = "특정 유저를 조회할 수 있다.")
	@GetMapping("/{userId}")
	public ResponseEntity<UserRead.Response> readUser(@PathVariable UUID userId){
		UserRead.Response response = userService.readUser(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "권한별 유저 조회", description = "새로 가입한 유저들을 권한으로 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<UserReadDto>> readNewUsers(
			@RequestParam UserRole type,
			Pageable pageable
	){
		return ResponseEntity.ok(userService.readNewUsers(type, pageable));
	}

	@Operation(summary = "회원탈퇴", description = "회원 탈퇴를 할 수 있다.")
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserDelete.Response> delete(
			@PathVariable UUID userId,
			@Valid @RequestBody UserDelete.Request request
	){
		UserDelete.Response response = userService.deleteUser(userId, request);
		return ResponseEntity.ok(response);
	}

}
