package com.artx.artx.domain.order.dto.summary;

import com.artx.artx.domain.delivery.domain.Delivery;
import com.artx.artx.domain.delivery.domain.DeliveryStatus;
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
