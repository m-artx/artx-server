package com.artx.artx.payment.service;

import com.artx.artx.order.entity.Order;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.payment.model.ReadPayment;
import com.artx.artx.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CashService implements PaymentService{

	private final PaymentRepository paymentRepository;

	@Value(value = "${api.orders}")
	private String ordersApiAddress;

	@Override
	public CreatePayment.Response processPayment(Order order) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ReadPayment.Response> readAllPayments(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable){
		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
		LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay().plusDays(1L) : null;

		Page<Payment> payments = paymentRepository.findAllByUserIdWithOrder(userId, startDateTime, endDateTime, pageable);
		return payments.map(payment -> ReadPayment.Response.from(ordersApiAddress, payment));
	}

}
