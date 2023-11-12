package com.artx.artx.payment.entity;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.order.entity.Order;
import com.artx.artx.payment.type.PaymentStatus;
import com.artx.artx.payment.type.PaymentType;
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
	private String tid;

	@Enumerated(EnumType.STRING)
	private PaymentType type;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@OneToOne(fetch = FetchType.LAZY)
	private Order order;

	private long totalAmount;

	public static Payment from(Order order, String tid, PaymentType paymentType, PaymentStatus paymentStatus) {
		return Payment.builder()
				.tid(tid)
				.totalAmount(order.getTotalAmount())
				.order(order)
				.type(paymentType)
				.status(paymentStatus)
				.build();
	}

	public void toPaymentSuccess() {
		this.status = PaymentStatus.PAYMENT_SUCCESS;
	}

	public void toPaymentFailure() {
		this.status = PaymentStatus.PAYMENT_FAILURE;
	}

	public void toPaymentCancel() {
		this.status = PaymentStatus.PAYMENT_CANCEL;
	}

	public boolean isCancelable() {
		if (this.status == PaymentStatus.PAYMENT_SUCCESS) {
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_PAYMENT_CANCEL);
	}
}
