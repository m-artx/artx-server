package com.artx.artx.domain.payment.domain;

import com.artx.artx.domain.order.domain.Order;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import com.artx.artx.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {

	@Id
	@UuidGenerator
	private UUID id;

	@Enumerated(EnumType.STRING)
	private PaymentType type;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private long totalAmount;

	public static Payment from(Order order, String tid) {
		return Payment.builder()
				.totalAmount(order.generateTotalAmount())
				.order(order)
				.build();
	}

	public void processPaymentSuccess() {
		this.status = PaymentStatus.PAYMENT_SUCCESS;
	}

	public void processPaymentFailure() {
		this.status = PaymentStatus.PAYMENT_FAILURE;
	}

	public void processPaymentCancel() {
		this.status = PaymentStatus.PAYMENT_CANCEL;
	}

	public boolean isCancelable() {
		if (this.status == PaymentStatus.PAYMENT_SUCCESS) {
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_PAYMENT_CANCEL);
	}
	public void setPaymentType(PaymentType paymentType) {
		this.type = paymentType;
	}
}
