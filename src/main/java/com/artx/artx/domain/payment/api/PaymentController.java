package com.artx.artx.domain.payment.api;


import com.artx.artx.domain.auth.domain.UserDetails;
import com.artx.artx.domain.payment.dto.PaymentRead;
import com.artx.artx.domain.payment.application.CashService;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@Tag(name = "결제")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final CashService cashService;

	@GetMapping
	public ResponseEntity<Page<PaymentRead.Response>> readAllPayments(
			@Nullable @RequestParam LocalDate startDate,
			@Nullable @RequestParam LocalDate endDate,
			Pageable pageable){
		return ResponseEntity.ok(cashService.readAllPayments(getUserId(), startDate, endDate, pageable));
	}

	public UUID getUserId() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userDetails.getUserId();

		} catch (ClassCastException e) {
			throw new BusinessException(ErrorCode.NEED_TO_CHECK_TOKEN);
		}
	}

}

