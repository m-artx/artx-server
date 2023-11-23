package com.artx.artx.common.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserReadDto {

	@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
	private UUID userId;

	@Schema(description = "아이디", example = "artxlover")
	private String username;

	public UserReadDto(UUID userId, String username) {
		this.userId = userId;
		this.username = username;
	}

}
