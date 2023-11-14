package com.artx.artx.payment.service;

import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.ReadPayment;
import com.artx.artx.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	@Transactional(readOnly = true)
	public Page<ReadPayment.Response> readAllPayments(UUID userId, Pageable pageable){
		Page<Payment> payments = paymentRepository.findAllByUserIdWithOrder(userId, pageable);
		return payments.map(ReadPayment.Response::from);
	}

}
