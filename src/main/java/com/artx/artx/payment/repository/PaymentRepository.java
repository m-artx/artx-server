package com.artx.artx.payment.repository;

import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.type.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

	@Modifying
	@Query("UPDATE Payment p SET p.status = :afterStatus WHERE p.createdAt < :sevenDaysAgo AND p.status = :beforeStatus")
	void updateExpiredPaymentToCancel(LocalDateTime sevenDaysAgo, PaymentStatus beforeStatus, PaymentStatus afterStatus);

	@Query(
			"SELECT p from Payment p " +
					"LEFT JOIN FETCH p.order o " +
					"WHERE o.user.userId = :userId " +
					"AND (:startDateTime IS NULL OR p.createdAt >= :startDateTime )" +
					"AND (:endDateTime IS NULL OR p.createdAt <= :endDateTime )"
	)
	Page<Payment> findAllByUserIdWithOrder(UUID userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
