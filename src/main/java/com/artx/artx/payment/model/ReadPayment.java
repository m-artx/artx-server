package com.artx.artx.payment.model;

import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.type.PaymentStatus;
import com.artx.artx.payment.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReadPayment {

	@Getter
	@Builder
	public static class Response{

		private UUID paymentId;
		private Long orderId;
		private String orderTitle;
		private Long paymentTotalAmount;
		private PaymentType paymentType;
		private PaymentStatus paymentStatus;
		private LocalDateTime paymentCreatedAt;

		public static Response from(Payment payment){
			return Response.builder()
					.paymentId(payment.getId())
					.orderId(payment.getOrder().getId())
					.paymentTotalAmount(payment.getTotalAmount())
					.orderTitle(payment.getOrder().getTitle())
					.paymentType(payment.getType())
					.paymentStatus(payment.getStatus())
					.paymentCreatedAt(payment.getCreatedAt())
					.build();
		}
	}
}
