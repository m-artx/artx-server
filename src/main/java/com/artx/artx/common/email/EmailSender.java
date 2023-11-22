package com.artx.artx.common.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Properties;
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
		String host = "smtp.gmail.com";
		int port = 587;

		// 프록시 서버 호스트 및 포트
		String proxyHost = "krmp-proxy.9rum.cc";
		int proxyPort = 3128;

		// 프록시 설정 추가
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// 프록시 설정 추가
		props.put("mail.smtp.socks.host", proxyHost);
		props.put("mail.smtp.socks.port", Integer.toString(proxyPort));

		// 세션 생성
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// 메시지 생성 및 전송
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject("[ARTX] 회원가입 완료를 위해 인증이 필요합니다.");
			message.setText("링크를 클릭하시면 인증이 완료됩니다.<a href=" + usersApiAddress + userId.toString() + "/email-auth\" + \">인증</a>");

			Transport.send(message);

			System.out.println("이메일 전송 완료.");

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
