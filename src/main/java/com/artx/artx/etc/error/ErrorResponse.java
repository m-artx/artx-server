package com.artx.artx.etc.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private String code;
	private String message;

}
