package com.artx.artx.product.model;

import lombok.Getter;

import java.util.UUID;

public class CreateCommission {

	@Getter
	public static class Request {

		private UUID userId;
		private String content;
	}
}
