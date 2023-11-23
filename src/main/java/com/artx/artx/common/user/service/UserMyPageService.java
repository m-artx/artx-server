package com.artx.artx.common.user.service;

import com.artx.artx.common.user.model.UserUpdate;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.common.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMyPageService {

	private final PasswordEncoder passwordEncoder;
	private final ImageService imageService;
	private final UserService userService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public void setProfileImage(UUID userId, MultipartFile file) {
		User user = userService.getUserByUserId(userId);
		String image = imageService.saveProfileImage(file);
		imageService.removeImage(user.getProfileImage());
		user.setProfileImage(image);
	}

	@Transactional
	public UserUpdate.Response updatePassword(UUID userId, UserUpdate.Request request) {
		User user = userService.getUserByUserId(userId);
		userService.isValidPassword(request.getPreviousPassword(), user.getPassword());
		user.setPassword(passwordEncoder.encode(request.getPresentPassword()));
		return UserUpdate.Response.builder().updatedAt(LocalDateTime.now()).build();
	}

}
