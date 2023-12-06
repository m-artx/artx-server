package com.artx.artx.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserAddressCreate {

	@Getter
	public static class Request {
		@Schema(description = "주소", example = "서울특별시 은천로 1길")
		@NotBlank
		private String address;

		@Schema(description = "상세 주소", example = "101호")
		@NotBlank
		private String addressDetail;
	}

}
