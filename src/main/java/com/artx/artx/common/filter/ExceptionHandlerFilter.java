package com.artx.artx.common.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try{
			filterChain.doFilter(request, response);
		} catch (Exception e){
			setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, e);
		}
	}

	private void setErrorResponse(HttpStatus status, HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(status.value());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(ErrorResponse.builder()
				.timestamp(LocalDateTime.now().toString())
				.status(status.value())
				.error(status.name())
				.message(e.getMessage())
				.path(request.getServletPath())
				.build()
				.convertToJson(objectMapper)
		);
	}
}

@Builder
class ErrorResponse implements Serializable {

	public String timestamp;
	public int status;
	public String error;
	public String message;
	public String path;

	public String convertToJson(ObjectMapper objectMapper) throws JsonProcessingException {
		return objectMapper.writeValueAsString(this);
	}

}