package com.artx.artx.user.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.model.CreateUser;
import com.artx.artx.user.model.ReadUser;
import com.artx.artx.user.model.ReadUserDto;
import com.artx.artx.user.repository.UserRepository;
import com.artx.artx.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public CreateUser.Response createUser(CreateUser.Request request){
		if(userRepository.existsByUsername(request.getUsername())){
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
	public User getUserByUserId(UUID userId){
		return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}


	@Transactional(readOnly = true)
	public User getUserByUserIdWithCart(UUID userId){
		return userRepository.findByIdWithCart(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public Page<ReadUserDto> getNewArtists(Pageable pageable){
		return userRepository.getNewArtists(UserRole.ARTIST, pageable);
	}

	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USERNAME));
	}

//	@Transactional
//	public CreateUser.Response createInquiry(CreateInquiry.Request request) {
//		request

//	}
}
