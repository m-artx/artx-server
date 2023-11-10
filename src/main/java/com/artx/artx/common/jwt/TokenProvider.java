package com.artx.artx.common.jwt;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Value("${jwt.expiration.access-token}")
	private Long ACCESS_TOKEN_EXPIRATION;

	@Value("${jwt.expiration.refresh-token}")
	private Long REFRESH_TOKEN_EXPIRATION;

	public String createToken(String username, TokenType type){

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
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}
	}




}
