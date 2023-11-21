package com.artx.artx.common.exception;

import com.artx.artx.common.error.ErrorCode;

public class JwtTokenExcpetion extends RuntimeException{

	private String code;

	public JwtTokenExcpetion(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
