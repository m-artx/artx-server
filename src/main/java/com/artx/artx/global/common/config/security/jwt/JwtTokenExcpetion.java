package com.artx.artx.global.common.config.security.jwt;

import com.artx.artx.global.common.error.ErrorCode;

public class JwtTokenExcpetion extends RuntimeException{

	private String code;

	public JwtTokenExcpetion(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
