package com.artx.artx.admin.notice.service;

import com.artx.artx.admin.notice.entity.Notice;
import com.artx.artx.admin.notice.model.NoticeCreate;
import com.artx.artx.admin.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

	private final NoticeRepository noticeRepository;

	@Transactional
	public NoticeCreate.Response createNotice(NoticeCreate.Request request){
		Notice notice = noticeRepository.save(Notice.from(request));
		return NoticeCreate.Response.of(notice);
	}

}
