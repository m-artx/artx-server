package com.artx.artx.domain.payment.domain;

import com.artx.artx.domain.model.BaseEntity;
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
