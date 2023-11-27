package com.artx.artx.payment.controller;

import com.artx.artx.payment.service.KakaoPayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "카카오페이 결제")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	@Value("${artx.payment-success-redirect}")
	private String successRedirectAddress;

	@Value("${artx.payment-fail-redirect}")
	private String failRedirectAddress;

	@Value("${artx.payment-cancel-redirect}")
	private String cancelRedirectAddress;


	@GetMapping("/approval")
	public void approval(
			@RequestParam(name = "partner_order_id") String orderId,
			@RequestParam String pg_token,
			HttpServletResponse response
	) throws IOException {
		kakaoPayService.approvalPayment(orderId, pg_token);
		response.sendRedirect(successRedirectAddress);
	}

	@PostMapping("/fail")
	public void fail(HttpServletResponse response) throws IOException {
		response.sendRedirect(failRedirectAddress);
	}

	@PostMapping("/cancel")
	public void cancel(HttpServletResponse response) throws IOException {
		response.sendRedirect(cancelRedirectAddress);
	}

}
