package com.artx.artx.payment.repository;

import com.artx.artx.payment.entity.KakaoPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoPaymentRepository extends JpaRepository<KakaoPayment, String> {
}
