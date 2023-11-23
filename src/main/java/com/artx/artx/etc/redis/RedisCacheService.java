package com.artx.artx.etc.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

	private final RedisTemplate<String, Long> redisTemplate;
	private final StringRedisTemplate stringRedisTemplate;

	public void countProductView(Long productId){
		HashOperations<String, Long, Long> ops = redisTemplate.opsForHash();
		ops.increment("productCountView", productId, 1);
	}

	public void saveRefreshToken(String username, String token){
		HashOperations<String, String, String> ops = redisTemplate.opsForHash();
		ops.put("refreshToken", username, token);
	}



}
