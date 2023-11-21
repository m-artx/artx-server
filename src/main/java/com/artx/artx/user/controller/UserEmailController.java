package com.artx.artx.user.controller;

import com.artx.artx.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/email")
public class UserEmailController {

	private final UserService userService;

	@Operation(summary = "이메일 인증", description = "이메일 인증을 완료한다.")
	@GetMapping("/auth")
	public ResponseEntity<String> emailAuth(
			@PathVariable UUID userId
	){
		userService.emailAuth(userId);
		return ResponseEntity.ok("인증 완료");
	}
//
//	@Operation(summary = "아이디 찾기", description = "이메일 계정을 통해 등록된 아이디를 찾는다.")
//	@GetMapping("/find-username")
//	public ResponseEntity<String> findUsernameByEmail(
//			@PathVariable UUID userId
//	){
//		userService.emailAuth(userId);
//		return ResponseEntity.ok("인증 완료");
//	}
//
//	@Operation(summary = "패스워드 초기화", description = "이메일 계정을 통해 등록된 아이디를 찾는다.")
//	@GetMapping("/init-username")
//	public ResponseEntity<String> InitPasswordByEmail(
//			@PathVariable UUID userId
//	){
//		userService.emailAuth(userId);
//		return ResponseEntity.ok("인증 완료");
//	}

}
