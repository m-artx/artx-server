package com.artx.artx.auth.model.token;


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