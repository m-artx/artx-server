package com.artx.artx.payment.repository;

import com.artx.artx.payment.entity.KakaoPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KakaoPaymentRepository extends JpaRepository<KakaoPayment, String> {


	@Query("SELECT k FROM KakaoPayment k LEFT JOIN FETCH k.payment kp LEFT JOIN FETCH kp.order kpo WHERE kpo.id = :orderId")
	Optional<KakaoPayment> fetchKakaoPaymentByOrderId(String orderId);

}
