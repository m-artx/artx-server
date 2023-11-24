package com.artx.artx.common.user.service;

import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.model.*;
import com.artx.artx.common.user.repository.UserRepository;
import com.artx.artx.common.user.type.UserRole;
import com.artx.artx.common.user.type.UserStatus;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserEmailService userEmailService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public UserCreate.Response createUser(UserCreate.Request request) {
		existUserByUsername(request.getUsername());
		User user = userRepository.save(User.from(request, passwordEncoder.encode(request.getPassword())));
		user.addAddress(user.getDefaultAddress());
		userEmailService.sendAuthenticationEmail(user);
		return UserCreate.Response.of(user);
	}

	@Transactional(readOnly = true)
	public UserRead.Response readUser(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		return UserRead.Response.of(user, imagesApiAddress);
	}

	@Transactional(readOnly = true)
	public Page<UserReadDto> readUsersByUserRole(UserRole userRole, Pageable pageable) {
		return userRepository.findUsersByUserRole(userRole, pageable);
	}

	@Transactional
	public UserDelete.Response deleteUser(UUID userId, UserDelete.Request request) {
		User user = getUserByUserId(userId);
		isValidPassword(request.getPassword(), user.getPassword());
		user.setDeleted(true);
		return UserDelete.Response
				.builder()
				.userDeletedAt(LocalDateTime.now())
				.build();
	}

	@Transactional
	public void resetPassword(UserEmail.PasswordRequest request) {
		User user = userRepository.findByEmailAndUsername(request.getEmail(), request.getUsername()).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		String newPassword = generateNewPassword();
		user.setPassword(passwordEncoder.encode(newPassword));
		userEmailService.sendInitPasswordEmail(user, newPassword);
	}

	private String generateNewPassword() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}

	@Transactional
	public void changeActiveStatus(UUID userId) {
		User user = getUserByUserId(userId);
		if (user.getUserStatus() == UserStatus.ACTIVE) {
			throw new BusinessException(ErrorCode.ALREADY_AUTHENTICATED_USER);
		}
		user.setUserStatus(UserStatus.ACTIVE);
	}

	@Transactional
	public UserEmail.UsernameResponse findUsernameByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL));
		return UserEmail.UsernameResponse.from(user);
	}

	@Transactional(readOnly = true)
	public boolean existUserByUsername(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
		}
		return true;
	}

	@Transactional(readOnly = true)
	public User getUserByUserId(UUID userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public User getUserWithAddressByUserId(UUID userId) {
		return userRepository.findWithAddressById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public User getUserWithCartById(UUID userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}



	public boolean isValidPassword(String previousPassword, String password) {
		if (!passwordEncoder.matches(previousPassword, password)) {
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}
		return true;
	}

	@Transactional(readOnly = true)
	public boolean isValidEmail(UserValid.Email request) {
		if(userRepository.existsByEmail(request.getEmail())){
			throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
		}

		return true;
	}

	@Transactional(readOnly = true)
	public boolean isValidUsername(UserValid.Username request) {
		if(userRepository.existsByUsername(request.getUsername())){
			throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
		}

		return true;
	}
}
