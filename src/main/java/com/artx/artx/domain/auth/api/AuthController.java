package com.artx.artx.domain.auth.api;

import com.artx.artx.domain.auth.dto.Login;
import com.artx.artx.domain.auth.dto.Logout;
import com.artx.artx.domain.auth.domain.UserDetails;
import com.artx.artx.domain.auth.dto.AccessToken;
import com.artx.artx.domain.auth.dto.RefreshToken;
import com.artx.artx.domain.auth.application.AuthService;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<Login.Response> login(@RequestBody Login.Request request, HttpServletResponse servletResponse) {
		Login.Response response = authService.login(request);
		RefreshToken refreshToken = RefreshToken.from(authService.createRefreshToken(request));

		Cookie refreshTokenCookie = generateRefreshTokenCookie(refreshToken);
		servletResponse.addCookie(refreshTokenCookie);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/reissue")
	public ResponseEntity<Login.Response> reissueAccessToken(HttpServletRequest request) {
		String refreshToken = findRefreshTokenFromCookie(request);
		boolean isValidRefreshToken = authService.isValidRefreshToken(refreshToken);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (isValidRefreshToken) {
			return ResponseEntity.ok(Login.Response
					.builder()
					.accessToken((AccessToken.from(authService.reissueAccessToken(refreshToken))))
					.userId(userDetails.getUserId())
					.userRole(userDetails.getUserRole())
					.build());
		}
		throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
	}

	private Cookie generateRefreshTokenCookie(RefreshToken refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getValue());
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(7));
		return refreshTokenCookie;
	}

	private String findRefreshTokenFromCookie(HttpServletRequest servletRequest) {
		Cookie[] cookies = servletRequest.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refreshToken")) {
				return cookie.getValue();
			}
		}
		return null;
	}

	@PostMapping("/logout")
	public ResponseEntity<Logout.Response> logout() {
		return ResponseEntity.ok(authService.logout());
	}

}
