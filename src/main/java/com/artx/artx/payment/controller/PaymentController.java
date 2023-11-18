package com.artx.artx.payment.controller;


import com.artx.artx.payment.model.ReadPayment;
import com.artx.artx.payment.service.CashService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Tag(name = "결제")
@RestController
@RequestMapping("/api/users/{userId}/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final CashService cashService;

	@GetMapping
	public ResponseEntity<Page<ReadPayment.Response>> readAllPayments(
			@PathVariable UUID userId,
			@Nullable @RequestParam LocalDate startDate,
			@Nullable @RequestParam LocalDate endDate,
			Pageable pageable){
		return ResponseEntity.ok(cashService.readAllPayments(userId, startDate, endDate, pageable));
	}

}

