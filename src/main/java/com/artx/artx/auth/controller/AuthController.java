package com.artx.artx.auth.controller;

import com.artx.artx.auth.model.Login;
import com.artx.artx.auth.model.Logout;
import com.artx.artx.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<Login.Response> login(@RequestBody Login.Request request){
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<Logout.Response> logout(){
		return ResponseEntity.ok(authService.logout());
	}

}
