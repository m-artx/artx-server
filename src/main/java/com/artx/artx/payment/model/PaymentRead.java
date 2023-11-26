package com.artx.artx.payment.model;

import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.type.PaymentStatus;
import com.artx.artx.payment.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
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
					.orderId(Objects.isNull(payment.getOrder()) ? "" : payment.getOrder().getId())
					.orderTitle(Objects.isNull(payment.getOrder()) ? "" : payment.getOrder().generateOrderTitle())
					.orderLink(Objects.isNull(payment.getOrder().getId()) ? "" : ordersApiAddress + payment.getOrder().getId())
					.paymentTotalAmount(payment.getTotalAmount())
					.paymentType(payment.getType())
					.paymentStatus(payment.getStatus())
					.paymentCreatedAt(payment.getCreatedAt())
					.build();
		}
	}

}
