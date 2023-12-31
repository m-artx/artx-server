package com.artx.artx.domain.user.dto.inquiry;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateInquiry {

	@Getter
	public static class Request {
		private UUID sellerId;
		private String title;
		private String content;
	}

	@Getter
	public static class Response {
		private Long inquiryId;
		private LocalDateTime createdAt;
	}
}
