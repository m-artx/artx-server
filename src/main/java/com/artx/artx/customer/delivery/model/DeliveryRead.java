package com.artx.artx.customer.delivery.model;

import lombok.Builder;
import lombok.Getter;

public class DeliveryRead {

	@Getter
	public static class Request {

	}

	@Getter
	@Builder
	public static class Response {

		private Long deliveryId;
	}

}
