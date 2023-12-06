package com.artx.artx.global.common.error.exception;

import com.artx.artx.global.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
