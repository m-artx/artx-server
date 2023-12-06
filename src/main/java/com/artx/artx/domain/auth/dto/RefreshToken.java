package com.artx.artx.domain.auth.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {
	private String value;

	public static RefreshToken from(String token) {
		return RefreshToken.builder()
				.value(token)
				.build();
	}

}


