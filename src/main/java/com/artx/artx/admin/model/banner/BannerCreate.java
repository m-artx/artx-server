package com.artx.artx.admin.model.banner;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

public class BannerCreate {

	@Getter
	public static class Request {
		@Schema(description = "작품 고유 식별 번호", example = "1")
		@NotNull
		private Long productId;
	}

	@Getter
	public static class Response {
		private LocalDateTime createdAt;
	}

}
