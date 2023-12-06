package com.artx.artx.domain.admin.user.service;

import com.artx.artx.domain.user.domain.User;
import com.artx.artx.domain.user.dto.UserRead;
import com.artx.artx.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public Page<UserRead.ResponseAll> readAllUsers(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		return users.map(UserRead.ResponseAll::of);
	}

}
