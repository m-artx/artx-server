//package com.artx.artx.common.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import java.util.Map;
//
//@SpringBootTest
//class RedisCacheServiceTest {
//
//	@Autowired
//	StringRedisTemplate redisTemplate;
//
//
//	@Test
//	void cacheTest(){
//		HashOperations<String, String, String> ops = redisTemplate.opsForHash();
//
//		for (int i = 0; i < 100; i++) {
//			ops.increment("productCountView", "1", 1);
//		}
//
//		Map<String, String> productCountView = ops.entries("productCountView");
//
//		for (String productId : productCountView.keySet()) {
//			String views = ops.get("productCountView", productId);
//			System.out.println(views);
//		}
//
//	}
//}