package com.artx.artx.payment.service;

import com.artx.artx.order.entity.Order;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.payment.model.ReadPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PaymentService {

	CreatePayment.Response processPayment(Order order);
	Page<ReadPayment.Response> readAllPayments(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
