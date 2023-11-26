package com.artx.artx.auth.service;

import com.artx.artx.auth.model.Login;
import com.artx.artx.auth.model.Logout;
import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.auth.model.token.AccessToken;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.jwt.TokenProvider;
import com.artx.artx.common.jwt.TokenType;
import com.artx.artx.common.redis.RedisCacheService;
import lombok.RequiredArgsConstructor;
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
	private final RedisCacheService redisCacheService;

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
		redisCacheService.saveRefreshToken(request.getUsername(), refreshToken);
		return refreshToken;
	}

	public boolean isValidRefreshToken(String refreshToken) {
		return tokenProvider.isValid(refreshToken);
	}

	public String reissueAccessToken(String refreshToken) {
		return tokenProvider.reissueAccessToken(refreshToken);
	}
}
