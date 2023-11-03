package com.artx.artx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Long> redisTemplate(){
		RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
		return redisTemplate;
	}

	@Bean
	public RedisConnectionFactory lettuceConnectionFactory(){
		return new LettuceConnectionFactory();
	}



}
