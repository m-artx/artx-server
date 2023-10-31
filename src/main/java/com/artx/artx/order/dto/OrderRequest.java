package com.artx.artx.order.dto;

import com.artx.artx.common.model.CommonOrder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class OrderRequest {

	@Getter
	public static class Create extends CommonOrder {
		private UUID userId;
		private List<OrderData> orderData;
		private DeliveryData deliveryData;
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


