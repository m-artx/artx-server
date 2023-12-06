package com.artx.artx.domain.payment.dto.kakaopay;

import lombok.Getter;

@Getter
public class CancelAvailableAmount {
	private Integer total;
	private Integer tax_free;
	private Integer vat;
	private Integer point;
	private Integer discount;
	private Integer green_deposit;
}
