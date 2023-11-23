package com.artx.artx.etc.schedule;

import com.artx.artx.customer.cart.repository.CartProductRepository;
import com.artx.artx.customer.order.repository.OrderRepository;
import com.artx.artx.customer.order.type.OrderStatus;
import com.artx.artx.customer.payment.repository.PaymentRepository;
import com.artx.artx.customer.payment.type.PaymentStatus;
import com.artx.artx.customer.product.entity.Product;
import com.artx.artx.customer.product.repository.ProductRepository;
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
	private final CartProductRepository cartProductRepository;
	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;

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
		cartProductRepository.deleteExpiredProducts(LocalDateTime.now().minusDays(30));
	}

	@Scheduled(cron = "0 55 * * * *")
	@Transactional
	public void updateExpiredOrderToCancel(){
		orderRepository.updateExpiredOrderToCancel(LocalDateTime.now().minusDays(7), OrderStatus.ORDER_READY, OrderStatus.ORDER_CANCEL);
		paymentRepository.updateExpiredPaymentToCancel(LocalDateTime.now().minusDays(7), PaymentStatus.PAYMENT_READY, PaymentStatus.PAYMENT_CANCEL);
	}






}
