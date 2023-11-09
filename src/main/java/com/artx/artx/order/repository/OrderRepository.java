package com.artx.artx.order.repository;

import com.artx.artx.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o " +
			"LEFT JOIN FETCH o.orderProducts op " +
			"LEFT JOIN FETCH o.payment " +
			"LEFT JOIN FETCH o.delivery " +
			"WHERE o.user.userId = :userId " +
			"ORDER BY o.createdAt DESC")
	Page<Order> findByUserIdWithOrderProductsAndPaymentAndDelivery(@Param("userId") UUID userId, Pageable pageable);


	@Query("SELECT o FROM Order o " +
			"LEFT JOIN FETCH o.payment " +
			"WHERE o.id = :orderId")
	Optional<Order> findByUserIdWithPayment(@Param("orderId") Long orderId);
}
