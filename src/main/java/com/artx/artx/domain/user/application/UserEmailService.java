package com.artx.artx.domain.user.application;

import com.artx.artx.infra.email.EmailCreate;
import com.artx.artx.domain.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserEmailService {

	private final RestTemplate restTemplate;

	@Value(value = "${api.email}")
	private String emailApiAddress;

	public void sendAuthenticationEmail(User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String body = objectMapper.writeValueAsString(EmailCreate.JoinRequest.builder().to(user.getEmail()).userId(user.getUserId()).build());

			HttpEntity httpEntity = new HttpEntity(body, headers);
			restTemplate.postForObject(emailApiAddress + "/join", httpEntity, Void.class);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	void sendInitPasswordEmail(User user, String newPassword) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String body = objectMapper.writeValueAsString(EmailCreate.PasswordRequest.builder().to(user.getEmail()).newPassword(newPassword).build());

			HttpEntity httpEntity = new HttpEntity(body, headers);
			restTemplate.postForObject(emailApiAddress + "/password", httpEntity, EmailCreate.Response.class);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
