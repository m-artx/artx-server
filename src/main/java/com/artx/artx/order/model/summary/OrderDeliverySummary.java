package com.artx.artx.order.model.summary;

import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.delivery.type.DeliveryStatus;
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
