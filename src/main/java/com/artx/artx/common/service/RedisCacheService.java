package com.artx.artx.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

	private final RedisTemplate<String, Long> redisTemplate;

	public void countProductView(Long productId){
		HashOperations<String, Long, Long> ops = redisTemplate.opsForHash();
		ops.increment("productCountView", productId, 1);
	}

}
