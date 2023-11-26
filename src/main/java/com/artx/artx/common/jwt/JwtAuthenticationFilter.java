package com.artx.artx.common.jwt;

import com.artx.artx.auth.model.UserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	private String extractAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		// 헤더에 "Bearer "로 시작하는지 확인하고, 토큰을 추출
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // "Bearer " 이후의 부분을 반환
		}

		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String accessToken = extractAccessToken(request);

		boolean isValid = tokenProvider.isValid(accessToken);

		if (isValid) {
			Authentication authentication = tokenProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails principal = (UserDetails) authentication.getPrincipal();

			log.info("Authentication: {}", authentication);
			log.info("USER_ID: {}", principal.getUserId());
			log.info("USER_ROLE: {}", principal.getUserRole());
			log.info("USER_NAME: {}", principal.getUsername());
		}

		filterChain.doFilter(request, response);
	}

}
