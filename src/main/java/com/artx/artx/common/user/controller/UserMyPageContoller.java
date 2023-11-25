package com.artx.artx.common.user.controller;

import com.artx.artx.common.auth.model.UserDetails;
import com.artx.artx.common.user.model.*;
import com.artx.artx.common.user.service.UserMyPageService;
import com.artx.artx.common.user.service.UserService;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Tag(name = "마이페이지")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class UserMyPageContoller {

	private final UserService userService;
	private final UserMyPageService userMyPageService;

	@Operation(summary = "유저 마이페이지 조회", description = "유저 마이페이지를 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<UserRead.Response> readMyPage() {
		UserRead.Response response = userService.readUser(getUserId());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "프로필 이미지 등록", description = "프로필 이미지를 등록할 수 있다.")
	@PostMapping("/image")
	public ResponseEntity<UserCreate.Response> addProfileImage(
			@RequestParam MultipartFile file
	) {
		userMyPageService.setProfileImage(getUserId(), file);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "비밀번호 변경", description = "패스워드를 변경할 수 있다.")
	@PatchMapping("/password")
	public ResponseEntity<UserUpdate.Response> updatePassword(
			@RequestBody UserUpdate.Request request
	) {
		UserUpdate.Response response = userMyPageService.updatePassword(getUserId(), request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "배송지 추가", description = "배송지를 추가할 수 있다.")
	@PostMapping("/addresses")
	public ResponseEntity<Void> addAddress(
			@RequestBody UserAddressCreate.Request request
	) {
		userMyPageService.addAddress(getUserId(), request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "배송지 조회", description = "배송지를 전체 조회할 수 있다.")
	@GetMapping("/addresses")
	public ResponseEntity<UserAddressRead.Response> readAddress(
	) {
		return ResponseEntity.ok(userMyPageService.getAllAddress(getUserId()));
	}

	@Operation(summary = "기본 배송지 변경", description = "기본 배송지를 변경할 수 있다.")
	@PatchMapping("/addresses")
	public ResponseEntity<Void> updateDefaultAddress(
			@RequestBody UserAddressUpdate.Request request
	) {
		userMyPageService.updateDefaultAddress(getUserId(), request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "배송지 삭제", description = "배송지를 삭제할 수 있다.")
	@DeleteMapping("/addresses/{addressId}")
	public ResponseEntity<Void> deleteAddress(
			@PathVariable Long addressId
	) {
		userMyPageService.deleteAddress(getUserId(), addressId);
		return ResponseEntity.ok().build();
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
