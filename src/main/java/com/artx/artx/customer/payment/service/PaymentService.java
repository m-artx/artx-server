package com.artx.artx.customer.payment.service;

import com.artx.artx.customer.order.entity.Order;
import com.artx.artx.customer.payment.model.PaymentCreate;
import com.artx.artx.customer.payment.model.PaymentRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PaymentService {

	PaymentCreate.Response processPayment(Order order);
	Page<PaymentRead.Response> readAllPayments(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
