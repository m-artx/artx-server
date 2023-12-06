package com.artx.artx.domain.payment.application;

import com.artx.artx.domain.order.domain.Order;
import com.artx.artx.domain.payment.dto.PaymentCreate;
import com.artx.artx.domain.payment.dto.PaymentRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PaymentService {

	PaymentCreate.Response processPayment(Order order);
	Page<PaymentRead.Response> readAllPayments(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
