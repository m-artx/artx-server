package com.artx.artx.admin.model.notice;

import com.artx.artx.admin.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class NoticeCreate {

	@Getter
	public static class Request {
		private String title;
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
