package com.artx.artx.email.controller;

import com.artx.artx.email.EmailCreate;
import com.artx.artx.email.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

	private final EmailSender emailSender;

	@PostMapping("/api/email")
	public ResponseEntity<Void> sendEmail(
			@RequestBody EmailCreate.Request request
	){
		emailSender.sendAuthenticationEmail(request.getTo(), request.getUserId());
		return ResponseEntity.ok().build();
	}
}
