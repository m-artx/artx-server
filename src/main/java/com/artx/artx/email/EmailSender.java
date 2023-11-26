package com.artx.artx.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailSender {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${api.users}")
	private String usersApiAddress;

	public void sendAuthenticationEmail(String receiver, UUID userId) {
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");


			helper.setFrom(username);
			helper.setTo(receiver);
			helper.setSubject("[ARTX] 회원가입 완료를 위해 인증이 필요합니다.");
			helper.setText("링크를 클릭하시면 인증이 완료됩니다.<a href=" + usersApiAddress + userId.toString() + "/email/auth" + ">인증</a>", true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void sendPasswordInitEmail(String receiver, String password) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(username);
			helper.setTo(receiver);
			helper.setSubject("[ARTX] 임시 패스워드를 확인해주세요.");
			helper.setText("임시 패스워드: " + password, true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
