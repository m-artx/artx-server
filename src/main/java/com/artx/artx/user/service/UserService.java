package com.artx.artx.user.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.user.dto.UserArtist;
import com.artx.artx.user.dto.UserRequest;
import com.artx.artx.user.dto.UserResponse;
import com.artx.artx.user.model.User;
import com.artx.artx.user.repository.UserRepository;
import com.artx.artx.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserResponse.Create createUser(UserRequest.Create request){
		if(userRepository.existsByUsername(request.getUsername())){
			throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
		}

		User user = userRepository.save(User.from(request));
		return UserResponse.Create.from(user);
	}

	@Transactional(readOnly = true)
	public UserResponse.Read readUser(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		return UserResponse.Read.from(user);
	}

	@Transactional(readOnly = true)
	public User getUserByUserId(UUID userId){
		return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public Page<UserArtist> getNewArtists(Pageable pageable){
		return userRepository.getNewArtists(UserRole.ARTIST, pageable);
	}
}
