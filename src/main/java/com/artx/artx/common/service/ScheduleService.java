package com.artx.artx.common.service;

import com.artx.artx.cart.repository.CartItemRepository;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

	private final RedisTemplate<String, Long> redisTemplate;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;

	@Scheduled(fixedDelay = 600000L)
	@Transactional
	public void productViewHandle(){
		HashOperations<String, Long, Long> ops = redisTemplate.opsForHash();
		Map<Long, Long> productCountViewEntries = ops.entries("productCountView");

		List<Product> allProducts = productRepository.findAllById(productCountViewEntries.keySet());

		for (Product product : allProducts) {
			product.setViews(productCountViewEntries.get(product.getId()));
		}

	}

	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void deleteExpiredCartItems(){
		cartItemRepository.deleteExpiredItems(LocalDateTime.now().minusDays(30));
	}

}
