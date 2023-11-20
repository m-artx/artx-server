package com.artx.artx.admin.entity;

import com.artx.artx.admin.model.notice.NoticeCreate;
import com.artx.artx.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Notice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	public static Notice from(NoticeCreate.Request request) {
		return Notice.builder()
				.title(request.getTitle())
				.content(request.getContent())
				.build();
	}

}
