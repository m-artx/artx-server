package com.artx.artx.payment.controller;

import com.artx.artx.payment.service.KakaoPayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "결제")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final KakaoPayService kakaoPayService;

	@GetMapping("/approval")
	public void approval(
			@RequestParam(name = "partner_order_id") Long orderId,
			@RequestParam String pg_token,
			HttpServletResponse response
	) throws IOException {
		kakaoPayService.approvalPayment(orderId, pg_token);
		response.sendRedirect("/success");
	}

	@PostMapping("/fail")
	public void fail(HttpServletResponse response) throws IOException {
		response.sendRedirect("/fail");
	}

	@PostMapping("/cancel")
	public ResponseEntity<Void> cancel() {
		return ResponseEntity.ok().build();
	}

}
