package com.artx.artx.user.service;

import com.artx.artx.admin.service.AdminPermissionService;
import com.artx.artx.common.email.EmailSender;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.model.*;
import com.artx.artx.user.model.permission.UserPermissionRequestCreate;
import com.artx.artx.user.model.permission.UserPermissionRequestRead;
import com.artx.artx.user.repository.UserRepository;
import com.artx.artx.user.type.UserRole;
import com.artx.artx.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ImageService imageService;
	private final AdminPermissionService permissionService;
	private final EmailSender emailSender;

	@Transactional
	public UserCreate.Response createUser(UserCreate.Request request) {
		existUserByUsername(request.getUsername());
		User user = userRepository.save(User.from(request, passwordEncoder.encode(request.getPassword())));

		emailSender.sendAuthenticationEmail(user.getEmail(), user.getUserId());
		return UserCreate.Response.of(user);
	}

	private boolean existUserByUsername(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
		}
		return true;
	}

	@Transactional(readOnly = true)
	public UserRead.Response readUser(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		return UserRead.Response.of(user);
	}

	@Transactional(readOnly = true)
	public User getUserByUserId(UUID userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public Page<UserReadDto> readNewUsers(UserRole userRole, Pageable pageable) {
		return userRepository.findNewUsersByUserRole(userRole, pageable);
	}

	@Transactional
	public void setProfileImage(UUID userId, MultipartFile file) {
		User user = getUserByUserId(userId);
		MultipartFile image = imageService.saveImage(file);
		imageService.deleteImage(user.getProfileImage());
		user.setProfileImage(image);
	}

	@Transactional
	public UserUpdate.Response updatePassword(UUID userId, UserUpdate.Request request) {
		User user = getUserByUserId(userId);
		isValidPassword(request.getPreviousPassword(), user.getPassword());
		user.setPassword(passwordEncoder.encode(request.getPresentPassword()));
		return UserUpdate.Response.builder().updatedAt(LocalDateTime.now()).build();
	}

	private boolean isValidPassword(String previousPassword, String password) {
		if (!passwordEncoder.matches(previousPassword, password)) {
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}
		return true;
	}

	@Transactional
	public UserDelete.Response deleteUser(UUID userId, UserDelete.Request request) {
		User user = getUserByUserId(userId);
		isValidPassword(request.getPassword(), user.getPassword());
		user.setDeleted(true);
		return UserDelete.Response.builder().userDeletedAt(LocalDateTime.now()).build();
	}

	@Transactional
	public UserPermissionRequestCreate.Response requestPermission(UUID userId, UserPermissionRequestCreate.Request request, List<MultipartFile> files) {
		isValidFileSize(2, files);
		User user = getUserByUserId(userId);
		isAlreadySamePermission(request, user);

		List<MultipartFile> images = imageService.saveImages(files);
		permissionService.createPermissionRequest(user, request, images);
		return UserPermissionRequestCreate.Response.of(LocalDateTime.now());
	}

	private boolean isAlreadySamePermission(UserPermissionRequestCreate.Request request, User user) {
		if (user.getUserRole() == request.getRole()) {
			throw new BusinessException(ErrorCode.ALREADY_SAME_PERMISSION);
		}
		return true;
	}

	private void isValidFileSize(int size, List<MultipartFile> files) {
		if (files.size() != size) {
			throw new BusinessException(ErrorCode.NEED_AT_TWO_FILES);
		}
	}

	public Page<UserPermissionRequestRead.Response> readRequestPermission(UUID userId, Pageable pageable) {
		return permissionService.getPermissionRequestPagesByUserId(userId, pageable).map(UserPermissionRequestRead.Response::from);
	}

	@Transactional
	public void emailAuth(UUID userId) {
		User user = getUserByUserId(userId);
		if (user.getUserStatus() == UserStatus.ACTIVE) {
			throw new BusinessException(ErrorCode.ALREADY_AUTHENTICATED_USER);
		}
		user.setUserStatus(UserStatus.ACTIVE);
	}

	@Transactional
	public UserHandle.UsernameResponse findUsernameByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL));
		return UserHandle.UsernameResponse.builder().username(user.getUsername()).build();
	}

	@Transactional
	public void passwordInitByEmail(UserHandle.Request request) {
		User user = userRepository.findByEmailAndUsername(request.getEmail(), request.getUsername()).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		String newPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		user.setPassword(passwordEncoder.encode(newPassword));

		emailSender.sendPasswordInitEmail(request.getEmail(), newPassword);
	}

}
