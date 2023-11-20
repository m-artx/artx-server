package com.artx.artx.admin.service;

import com.artx.artx.admin.entity.Notice;
import com.artx.artx.admin.model.notice.NoticeCreate;
import com.artx.artx.admin.repository.NoticeRepository;
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
