package com.artx.artx.customer.payment.entity;

import com.artx.artx.etc.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayment extends BaseEntity {

	@Id
	@Column(nullable = false)
	private String tid;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id", nullable = false, unique = true)
	private Payment payment;

}
