package com.artx.artx.domain.auth.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessToken {

	private String value;

	public static AccessToken from(String token) {
		return AccessToken.builder()
				.value(token)
				.build();
	}

}