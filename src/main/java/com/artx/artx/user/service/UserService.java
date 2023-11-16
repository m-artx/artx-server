package com.artx.artx.user.service;

import com.artx.artx.admin.service.PermissionService;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.model.*;
import com.artx.artx.user.model.permission.CreateUserPermissionRequest;
import com.artx.artx.user.model.permission.ReadUserPermissionRequest;
import com.artx.artx.user.repository.UserRepository;
import com.artx.artx.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ImageService imageService;
	private final PermissionService permissionService;

	@Transactional
	public CreateUser.Response createUser(CreateUser.Request request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
		}

		User user = userRepository.save(User.from(request));
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		return CreateUser.Response.from(user);
	}

	@Transactional(readOnly = true)
	public ReadUser.Response readUser(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		return ReadUser.Response.from(user);
	}

	@Transactional(readOnly = true)
	public User getUserByUserId(UUID userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}


	@Transactional(readOnly = true)
	public User getUserByUserIdWithCart(UUID userId) {
		return userRepository.findByIdWithCart(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public Page<ReadUserDto> getNewArtists(Pageable pageable) {
		return userRepository.getNewArtists(UserRole.ARTIST, pageable);
	}

	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USERNAME));
	}

	@Transactional
	public void setProfileImage(UUID userId, MultipartFile file) {
		User user = getUserByUserId(userId);
		MultipartFile image = imageService.saveImage(file);
		imageService.deleteImage(user.getProfileImage());
		user.setProfileImage(image);
	}

	@Transactional
	public UpdateUser.Response updatePassword(UUID userId, UpdateUser.Request request) {
		User user = getUserByUserId(userId);
		if (!passwordEncoder.matches(request.getPreviousPassword(), user.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}
		;
		user.setPassword(passwordEncoder.encode(request.getPresentPassword()));
		return UpdateUser.Response.builder().updatedAt(LocalDateTime.now()).build();
	}

	@Transactional
	public DeleteUser.Response deleteUser(UUID userId, DeleteUser.Request request) {
		User user = getUserByUserId(userId);
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}
		;
		user.setDeleted(true);
		return DeleteUser.Response.builder().deletedAt(LocalDateTime.now()).build();
	}

	@Transactional
	public CreateUserPermissionRequest.Response requestPermission(UUID userId, CreateUserPermissionRequest.Request request, List<MultipartFile> files) {

		if (files.size() != 2) {
			throw new BusinessException(ErrorCode.NEED_AT_TWO_FILES);
		}

		User user = getUserByUserId(userId);

		if (user.getUserRole() == request.getRole()) {
			throw new BusinessException(ErrorCode.ALREADY_SAME_PERMISSION);
		}

		List<MultipartFile> images = imageService.saveImages(files);
		permissionService.createPermissionRequest(user, request, images);
		return CreateUserPermissionRequest.Response.builder().createdAt(LocalDateTime.now()).build();
	}

	public Page<ReadUserPermissionRequest.Response> readRequestPermission(UUID userId, Pageable pageable) {
		return permissionService.getPermissionRequestPagesByUserId(userId, pageable)
				.map(ReadUserPermissionRequest.Response::from);
	}

	public Map<UserRole, Long> readAllDailyUserAndArtistCounts() {
		List<Object[]> objects = userRepository.readAllDailyUserAndArtistCounts();

		Map<UserRole, Long> map = new HashMap<>();
		for (Object[] object : objects) {
			UserRole role = (UserRole) object[0];
			Long count = (Long) object[1];
			map.put(role, count);
		}
		return map;
	}


//	@Transactional
//	public CreateUser.Response createInquiry(CreateInquiry.Request request) {
//		request

//	}
}
