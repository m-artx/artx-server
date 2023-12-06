package com.artx.artx.domain.payment.dto.kakaopay;

import lombok.Getter;

@Getter
public class Amount {
	private Long total;
	private Long tax_free;
	private Long vat;
	private Long point;
	private Long discount;
	private Long green_deposi;
}
