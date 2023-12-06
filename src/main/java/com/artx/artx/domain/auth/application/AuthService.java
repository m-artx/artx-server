package com.artx.artx.domain.auth.application;

import com.artx.artx.domain.auth.domain.UserDetails;
import com.artx.artx.domain.auth.dto.AccessToken;
import com.artx.artx.domain.auth.dto.Login;
import com.artx.artx.domain.auth.dto.Logout;
import com.artx.artx.global.common.config.security.jwt.TokenProvider;
import com.artx.artx.global.common.config.security.jwt.TokenType;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final TokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;
	private final RedisTemplate<String, Long> redisTemplate;

	@Transactional(readOnly = true)
	public Login.Response login(Login.Request request) {
		try {
			//검증 실패 시 예외 발생
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return Login.Response.builder()
					.accessToken(AccessToken.from(tokenProvider.createToken(userDetails.getUserId(), request.getUsername(), TokenType.ACCESS_TOKEN)))
					.userId(userDetails.getUserId())
					.userRole(userDetails.getUserRole())
					.build();
		} catch (UsernameNotFoundException e) {
			throw new BusinessException(ErrorCode.INVALID_USERNAME);
		} catch (BadCredentialsException e){
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}
	}

	@Transactional(readOnly = true)
	public Logout.Response logout() {
		return Logout.Response.builder()
				.accessToken(AccessToken.from(""))
				.build();
	}

	public String createRefreshToken(Login.Request request) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String refreshToken = tokenProvider.createToken(userDetails.getUserId(), request.getUsername(), TokenType.REFRESH_TOKEN);
		saveRefreshToken(request.getUsername(), refreshToken);
		return refreshToken;
	}

	public boolean isValidRefreshToken(String refreshToken) {
		return tokenProvider.isValid(refreshToken);
	}

	public String reissueAccessToken(String refreshToken) {
		return tokenProvider.reissueAccessToken(refreshToken);
	}


	public void saveRefreshToken(String username, String token){
		HashOperations<String, String, String> ops = redisTemplate.opsForHash();
		ops.put("refreshToken", username, token);
	}
}
