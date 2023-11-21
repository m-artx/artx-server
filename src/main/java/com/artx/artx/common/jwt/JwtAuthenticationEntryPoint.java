package com.artx.artx.common.jwt;

import com.artx.artx.common.error.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		// CustomException이 발생했을 때 처리 로직을 작성합니다.
		// 여기에서는 간단히 JSON 응답으로 처리한 예시를 보여줍니다.

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		Map<String, Object> responseBody = new LinkedHashMap<>();

		responseBody.put("timestamp", LocalDateTime.now().toString());
		responseBody.put("status", "401");
		responseBody.put("error", ErrorCode.INVALID_ACCESS_TOKEN);
		responseBody.put("path", request.getServletPath());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getWriter(), responseBody);
	}
}
