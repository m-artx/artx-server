package com.artx.artx.global.common.error.handler;

import com.artx.artx.global.common.error.ErrorResponse;
import com.artx.artx.global.common.error.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(
						ErrorResponse.builder()
								.code(exception.getErrorCode().getCode())
								.message(exception.getErrorCode().getMessage())
								.build()
				);
	}
}
