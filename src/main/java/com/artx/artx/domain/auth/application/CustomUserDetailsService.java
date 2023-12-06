package com.artx.artx.domain.auth.application;


import com.artx.artx.domain.user.domain.UserStatus;
import com.artx.artx.domain.user.domain.User;
import com.artx.artx.domain.user.dao.UserRepository;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
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
		return new com.artx.artx.domain.auth.domain.UserDetails(user.getUserId(), user.getUsername(), user.getPassword(), user.getUserRole());
	}

}
