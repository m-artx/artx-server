package com.artx.artx.admin.notice.controller;

import com.artx.artx.admin.notice.model.NoticeCreate;
import com.artx.artx.admin.notice.service.AdminNoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin/notice")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminNoticeController {

	private final AdminNoticeService noticeService;

	@PostMapping
	public ResponseEntity<NoticeCreate.Response> createNotice(@RequestPart NoticeCreate.Request request){
		return ResponseEntity.ok(noticeService.createNotice(request));
	}

}

