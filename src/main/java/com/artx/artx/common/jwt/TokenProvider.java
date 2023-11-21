package com.artx.artx.common.jwt;

import com.artx.artx.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private final CustomUserDetailsService userDetailsService;

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Value("${jwt.expiration.access-token}")
	private Long ACCESS_TOKEN_EXPIRATION;

	@Value("${jwt.expiration.refresh-token}")
	private Long REFRESH_TOKEN_EXPIRATION;

	public String createToken(UUID userId, String username, TokenType type){

		Date now = new Date();
		Date exp = null;

		if(type == TokenType.ACCESS_TOKEN) {
			exp = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION);
		}

		if(type == TokenType.REFRESH_TOKEN) {
			exp = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION);
		}

		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

		return Jwts.builder()
				.setExpiration(exp)
				.setSubject(username)
				.claim("userId", userId)
				.setIssuedAt(now)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public Claims getClaim(String token){
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token) //검증 시 Exception 발생 포인트
				.getBody();
	}

	public boolean isValid(String token){
		try{
			getClaim(token);
			return true;
		} catch (Exception e){

		}
		return false;
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
		String username = claims.getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String reissueAccessToken(String refreshToken) {
		Claims claim = getClaim(refreshToken);
		String username = claim.getSubject();
		return createToken(null, username, TokenType.ACCESS_TOKEN);
	}
}
