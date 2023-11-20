package com.artx.artx.payment.model;

import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.type.PaymentStatus;
import com.artx.artx.payment.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentRead {

	@Getter
	@Builder
	public static class Response extends PaymentCreate.Response {

		private UUID paymentId;
		private String orderId;
		private String orderTitle;
		private String orderLink;
		private Long paymentTotalAmount;
		private PaymentType paymentType;
		private PaymentStatus paymentStatus;
		private LocalDateTime paymentCreatedAt;

		public static Response of(String ordersApiAddress, Payment payment) {
			return Response.builder()
					.paymentId(payment.getId())
					.orderId(payment.getOrder().getId())
					.orderTitle(payment.getOrder().generateOrderTitle())
					.orderLink(ordersApiAddress + payment.getOrder().getId())
					.paymentTotalAmount(payment.getTotalAmount())
					.paymentType(payment.getType())
					.paymentStatus(payment.getStatus())
					.paymentCreatedAt(payment.getCreatedAt())
					.build();
		}
	}

}
