package com.artx.artx.payment.controller;


import com.artx.artx.payment.model.ReadPayment;
import com.artx.artx.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "결제")
@RestController
@RequestMapping("/api/users/{userId}/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping
	public ResponseEntity<Page<ReadPayment.Response>> readAllPayments(@PathVariable UUID userId, Pageable pageable){
		return ResponseEntity.ok(paymentService.readAllPayments(userId, pageable));
	}
}
