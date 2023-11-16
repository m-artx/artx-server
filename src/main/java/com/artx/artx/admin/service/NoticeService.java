package com.artx.artx.admin.service;

import com.artx.artx.admin.entity.Notice;
import com.artx.artx.admin.model.notice.CreateNotice;
import com.artx.artx.admin.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	@Transactional
	public CreateNotice.Response createNotice(CreateNotice.Request request){
		Notice notice = noticeRepository.save(Notice.from(request));
		return CreateNotice.Response.from(notice);
	}

}
