package com.artx.artx.domain.admin.notice.model;

import com.artx.artx.domain.admin.notice.entity.Notice;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class NoticeCreate {

	@Getter
	public static class Request {
		@NotNull
		private String title;

		@NotNull
		private String content;
	}

	@Getter
	@Builder
	public static class Response {
		private LocalDateTime createdAt;

		public static Response of(Notice notice){
			return Response.builder()
					.createdAt(notice.getCreatedAt())
					.build();
		}
	}

}
