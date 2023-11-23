package com.artx.artx.common.auth.service;


import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.repository.UserRepository;
import com.artx.artx.common.user.type.UserStatus;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		if(user.getUserStatus() == UserStatus.INACTIVE){
			throw new BusinessException(ErrorCode.NOT_AUTHENTICATED_USER);
		}
		return new com.artx.artx.common.auth.model.UserDetails(user.getUserId(), user.getUsername(), user.getPassword(), user.getUserRole());
	}

}
