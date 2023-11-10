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
	INVALID_USERNAME("USER-005", "INVALID USERNAME"),
	INVALID_PASSWORD("USER-005", "INVALID PASSWORD"),

	PRODUCT_NOT_FOUND("PRODUCT-001", "PRODUCT NOT FOUND"),
	PRODUCT_CATEGORY_NOT_FOUND("PRODUCT-002", "PRODUCT CATEGORY NOT FOUND"),
	CREATE_PRODUCT_ONLY_ARTIST("PRODUCT-003", "CREATE PRODUCT ONLY ARTIST"),
	CAN_NOT_DECREASE("PRODUCT-004", "CANNOT DECREASE"),
	NOT_ENOUGH_QUANTITY("PRODUCT-005", "NOT ENOUGH QUANTITY"),

	ORDER_NOT_FOUND("ORDER-001", "ORDER NOT FOUND"),
	CAN_NOT_ORDER_CANCEL("ORDER-002", "CAN NOT ORDER CANCEL"),


	FAILED_KAKAOPAY_PAYMENT("PAYMENT-001", "FAILED KAKAOPAY PAYMENT"),
	PAYMENT_NOT_FOUND("PAYMENT-002", "PAYMENT NOT FOUND"),
	CAN_NOT_PAYMENT_CANCEL("PAYMENT-003", "CAN NOT PAYMENT CANCEL"),

	DUPLICATED_CARTITEM("CART-001", "DUPLICATED CARTITEM"),
	CART_NOT_FOUND("CART-002", "CART NOT FOUND"),
	CART_ITEM_NOT_FOUND("CART-002", "CART ITEM NOT FOUND"),

	DUPLICATED_BANNER("BANNER-001", "DUPLICATED BANNER"),

	CAN_NOT_DELIVERY_CANCEL("DELIVERY-001", "CAN NOT DELIVERY CANCEL"),

	FAILED_TO_CREATE_CATEGORY("ADMIN-001", "FAILED TO CREATE CATEGORY"),

	TOKEN_TYPE_NOT_FOUND("TOKEN-001", "TOKEN TYPE NOT FOUND"),
	INVALID_TOKEN("TOKEN-002", "INVALID TOKEN");







	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;

}
