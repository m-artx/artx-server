package com.artx.artx.payment.repository;

import com.artx.artx.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

	@Query(
			"SELECT p FROM Payment p " +
					"LEFT JOIN FETCH p.order po " +
					"LEFT JOIN FETCH po.user u " +
					"WHERE p.order.id = :orderId"
	)
	Optional<Payment> findByOrder_Id(@Param("orderId") Long orderId);
}
