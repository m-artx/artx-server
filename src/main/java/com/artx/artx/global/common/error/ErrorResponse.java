package com.artx.artx.global.common.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private String code;
	private String message;

}
