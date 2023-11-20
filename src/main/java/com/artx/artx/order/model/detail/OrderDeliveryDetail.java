package com.artx.artx.order.model.detail;

import com.artx.artx.common.model.Address;
import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.delivery.type.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDeliveryDetail {

	private String deliveryId;
	private String deliveryReceiver;
	private String deliveryReceiverPhoneNumber;
	private String deliveryReceiverAddress;
	private String deliveryReceiverAddressDetail;
	private DeliveryStatus deliveryStatus;

	public static OrderDeliveryDetail of(Delivery delivery) {
		Address address = delivery.getAddress();

		return OrderDeliveryDetail.builder()
				.deliveryId(delivery.getId())
				.deliveryReceiver(delivery.getReceiver())
				.deliveryReceiverPhoneNumber(delivery.getReceiverPhoneNumber())
				.deliveryReceiverAddress(address.getAddress())
				.deliveryReceiverAddressDetail(address.getAddressDetail())
				.deliveryStatus(delivery.getStatus())
				.build();
	}

}