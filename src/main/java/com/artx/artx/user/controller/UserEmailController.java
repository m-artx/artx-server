package com.artx.artx.user.controller;

import com.artx.artx.user.model.UserHandle;
import com.artx.artx.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}")
public class UserEmailController {

	private final UserService userService;

	@Operation(summary = "이메일 인증", description = "이메일 인증을 완료한다.")
	@GetMapping("/email/auth")
	public void emailAuth(
			@PathVariable UUID userId,
			HttpServletResponse response
	){
		userService.emailAuth(userId);
		try {
			response.sendRedirect("https://artx-client.vercel.app/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Operation(summary = "아이디 찾기", description = "이메일 계정을 통해 등록된 아이디를 찾는다.")
	@PostMapping("/find-username")
	public ResponseEntity<UserHandle.UsernameResponse> findUsernameByEmail(
			@RequestBody UserHandle.Request request
	){
		return ResponseEntity.ok(userService.findUsernameByEmail(request.getEmail()));
	}

	@Operation(summary = "패스워드 초기화", description = "이메일 계정을 통해 등록된 아이디를 찾는다.")
	@PostMapping("/init-password")
	public ResponseEntity<Void> InitPasswordByEmail(
			@RequestBody UserHandle.Request request
	){
		userService.passwordInitByEmail(request);
		return ResponseEntity.ok().build();
	}

}
