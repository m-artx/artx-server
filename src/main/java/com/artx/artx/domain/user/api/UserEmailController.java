package com.artx.artx.domain.user.api;

import com.artx.artx.domain.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@Tag(name = "유저 메일")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class UserEmailController {

	private final UserService userService;

	@Operation(summary = "이메일 인증", description = "이메일 인증을 완료한다.")
	@GetMapping("/auth")
	public void authentication(
			@RequestParam UUID userId,
			HttpServletResponse response
	){
		userService.changeActiveStatus(userId);
		try {
			response.sendRedirect("https://artx-client.vercel.app/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
