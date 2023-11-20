package com.artx.artx.order.service;

import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<Order, String> {

	@Query(
			"SELECT op FROM OrderProduct op " +
					"LEFT JOIN FETCH op.order opo " +
					"LEFT JOIN FETCH op.product opp " +
					"LEFT JOIN FETCH opp.user oppu " +
					"WHERE oppu.userId = :userId " +
					"ORDER BY opo.createdAt DESC"
	)
	Page<OrderProduct> fetchOrderProductsByUserId(UUID userId, Pageable pageable);

}