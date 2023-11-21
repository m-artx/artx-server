package com.artx.artx.user.controller;

import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.user.model.UserCreate;
import com.artx.artx.user.model.UserDelete;
import com.artx.artx.user.model.UserRead;
import com.artx.artx.user.model.UserUpdate;
import com.artx.artx.user.service.UserService;
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

	@Operation(summary = "유저 마이페이지 조회", description = "유저 마이페이지를 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<UserRead.Response> readMyPage(){
		UserRead.Response response = userService.readUser(getUserId());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "프로필 이미지 등록", description = "프로필 이미지를 등록할 수 있다.")
	@PostMapping("/image")
	public ResponseEntity<UserCreate.Response> addProfileImage(
			@RequestParam MultipartFile file){
		userService.setProfileImage(getUserId(), file);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "비밀번호 변경", description = "패스워드를 변경할 수 있다.")
	@PatchMapping("/password")
	public ResponseEntity<UserUpdate.Response> updatePassword(
			@RequestBody UserUpdate.Request request
	){
		UserUpdate.Response response = userService.updatePassword(getUserId(), request);
		return ResponseEntity.ok(response);
	}



	public UUID getUserId(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUserId();
	}
	
}
