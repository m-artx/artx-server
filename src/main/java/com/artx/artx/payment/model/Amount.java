package com.artx.artx.payment.model;

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
