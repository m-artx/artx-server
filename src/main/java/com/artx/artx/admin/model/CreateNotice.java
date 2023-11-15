package com.artx.artx.admin.model;

import com.artx.artx.admin.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CreateNotice {

	@Getter
	public static class Request {
		private String title;
		private String content;
	}

	@Getter
	@Builder
	public static class Response {
		private Long noticeId;
		private LocalDateTime createdAt;

		public static Response from(Notice notice){
			return Response.builder()
					.noticeId(notice.getId())
					.createdAt(notice.getCreatedAt())
					.build();
		}
	}
}
