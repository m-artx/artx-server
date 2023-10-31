package com.artx.artx.common.model;

import com.artx.artx.order.dto.OrderRequest;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public abstract class CommonOrder {

	@Getter
	public static class Create{
		private UUID userId;
		private List<OrderRequest.OrderData> orderData;
		private OrderRequest.DeliveryData deliveryData;
	}

	@Getter
	public static class OrderData {
		private Long productId;
		private Long quantity;
	}

	@Getter
	public static class DeliveryData {
		private String receiver;
		private String phoneNumber;
		private String address;
		private String addressDetail;
	}
}
