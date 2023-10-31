package com.artx.artx.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	MUST_BE_MORE_THAN_ZERO("COMMON-001", "MUST BE MORE THAN ZERO"),
	DIFFERENT_CURRENCY("COMMON-002", "DIFFERENT CURRENCY"),

	USER_NOT_FOUND("USER-001", "USER NOT FOUND"),
	DUPLICATED_USERNAME("USER-002", "DUPLICATED USERNAME"),
	DUPLICATED_NICKNAME("USER-003", "DUPLICATED NICKNAME"),
	DUPLICATED_EMAIL("USER-004", "DUPLICATED EMAIL"),

	PRODUCT_NOT_FOUND("PRODUCT-001", "PRODUCT NOT FOUND"),
	PRODUCT_CATEGORY_NOT_FOUND("PRODUCT-002", "PRODUCT CATEGORY NOT FOUND"),
	CREATE_PRODUCT_ONLY_ARTIST("PRODUCT-003", "CREATE PRODUCT ONLY ARTIST"),
	CAN_NOT_DECREASE("PRODUCT-004", "CANNOT DECREASE"),
	NOT_ENOUGH_QUANTITY("PRODUCT-005", "NOT ENOUGH QUANTITY"),


	DUPLICATED_CARTITEM("CART-001", "DUPLICATED CARTITEM"),
	CART_NOT_FOUND("CART-002", "CART_NOT_FOUND"),
	CART_ITEM_NOT_FOUND("CART-002", "CART_ITEM_NOT_FOUND");





	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;

}
