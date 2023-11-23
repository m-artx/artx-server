package com.artx.artx.customer.order.model.summary;

import com.artx.artx.customer.delivery.entity.Delivery;
import com.artx.artx.customer.delivery.type.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDeliverySummary {

	private String deliveryId;
	private DeliveryStatus deliveryStatus;

	public static OrderDeliverySummary of(Delivery delivery) {
		return OrderDeliverySummary.builder()
				.deliveryId(delivery.getId())
				.deliveryStatus(delivery.getStatus())
				.build();
	}

}
