package com.artx.artx.user.controller;

import com.artx.artx.user.model.*;
import com.artx.artx.user.model.permission.UserPermissionRequestCreate;
import com.artx.artx.user.model.permission.UserPermissionRequestRead;
import com.artx.artx.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "가입 정보와 함께 회원가입을 할 수 있다.")
	@PostMapping
	public ResponseEntity<UserCreate.Response> createUser(@RequestBody UserCreate.Request request){
		UserCreate.Response response = userService.createUser(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "특정 유저 조회", description = "특정 유저를 조회할 수 있다.")
	@GetMapping("/{userId}")
	public ResponseEntity<UserRead.Response> readUser(@PathVariable UUID userId){
		UserRead.Response response = userService.readUser(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "신규 가입 작가 조회", description = "새로 가입한 작가들을 조회할 수 있다.")
	@GetMapping("/new-artist")
	public ResponseEntity<Page<UserReadDto>> readNewArtists(Pageable pageable){
		return ResponseEntity.ok(userService.getNewArtists(pageable));
	}

	@Operation(summary = "프로필 이미지 등록", description = "프로필 이미지를 등록할 수 있다.")
	@PostMapping("/{userId}/image")
	public ResponseEntity<UserCreate.Response> addProfileImage(
			@PathVariable UUID userId,
			@RequestParam MultipartFile file){
		userService.setProfileImage(userId, file);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "비밀번호 변경", description = "패스워드를 변경할 수 있다.")
	@PatchMapping("/{userId}/password")
	public ResponseEntity<UserUpdate.Response> updatePassword(
			@PathVariable UUID userId,
			@RequestBody UserUpdate.Request request
	){
		UserUpdate.Response response = userService.updatePassword(userId, request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "권한 변경 신청", description = "권한 변경을 신청할 수 있다.")
	@PostMapping("/{userId}/permission-request")
	public ResponseEntity<UserPermissionRequestCreate.Response> createRequestPermission(
			@PathVariable UUID userId,
			@RequestPart List<MultipartFile> files,
			@RequestPart UserPermissionRequestCreate.Request request
	){
		UserPermissionRequestCreate.Response response = userService.requestPermission(userId, request, files);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "권한 변경 신청 조회", description = "권한 변경을 신청할 수 있다.")
	@GetMapping("/{userId}/permission-request")
	public ResponseEntity<Page<UserPermissionRequestRead.Response>> readRequestPermission(
			@PathVariable UUID userId,
			Pageable pageable
	){
		return ResponseEntity.ok(userService.readRequestPermission(userId, pageable));
	}

	@Operation(summary = "회원탈퇴", description = "회원 탈퇴를 할 수 있다.")
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserDelete.Response> delete(
			@PathVariable UUID userId,
			@RequestBody UserDelete.Request request
	){
		UserDelete.Response response = userService.deleteUser(userId, request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "이메일 인증", description = "이메일 인증을 완료한다.")
	@GetMapping("/{userId}/email-auth")
	public ResponseEntity<String> emailAuth(
			@PathVariable UUID userId
	){
		userService.emailAuth(userId);
		return ResponseEntity.ok("인증 완료");
	}
}
