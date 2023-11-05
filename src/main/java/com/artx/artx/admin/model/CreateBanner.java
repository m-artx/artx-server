package com.artx.artx.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class CreateBanner {

	@Getter
	public static class Request {
		@Schema(description = "작품 고유 식별 번호", nullable = false, example = "1")
		private Long productId;
	}
}
