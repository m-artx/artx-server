package com.artx.artx.payment.repository;

import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.type.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

	@Query(
			"SELECT p FROM Payment p " +
					"LEFT JOIN FETCH p.order po " +
					"LEFT JOIN FETCH po.user u " +
					"WHERE p.order.id = :orderId"
	)
	Optional<Payment> findByOrder_Id(@Param("orderId") String orderId);

	@Modifying
	@Query("UPDATE Payment p SET p.status = :afterStatus WHERE p.createdAt < :sevenDaysAgo AND p.status = :beforeStatus")
	void updateExpiredPaymentToCancel(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo, @Param("beforeStatus") PaymentStatus beforeStatus, PaymentStatus afterStatus);

	@Query(
			"SELECT p from Payment p " +
					"LEFT JOIN FETCH p.order o " +
					"WHERE o.user.userId = :userId " +
					"AND (:startDateTime IS NULL OR p.createdAt >= :startDateTime )" +
					"AND (:endDateTime IS NULL OR p.createdAt <= :endDateTime )"
	)
	Page<Payment> findAllByUserIdWithOrder(@Param("userId") UUID userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
