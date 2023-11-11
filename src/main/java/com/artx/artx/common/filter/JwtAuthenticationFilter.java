package com.artx.artx.common.filter;

import com.artx.artx.common.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String accessToken = extractAccessToken((HttpServletRequest) request);
		boolean isValid = tokenProvider.isValid(accessToken);

		try {
			if (isValid) {
				Authentication authentication = tokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			chain.doFilter(request, response);
		} catch (Exception e) {

		}
	}
}
