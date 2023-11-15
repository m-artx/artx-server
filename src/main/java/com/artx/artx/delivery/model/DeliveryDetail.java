package com.artx.artx.delivery.model;

import com.artx.artx.common.model.Address;
import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.order.type.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryDetail {

	private String deliveryReceiver;
	private String deliveryReceiverPhoneNumber;
	private String deliveryReceiverAddress;
	private String deliveryReceiverAddressDetail;
	private DeliveryStatus deliveryStatus;

	public static DeliveryDetail from(Delivery delivery) {
		Address address = delivery.getAddress();

		return DeliveryDetail.builder()
				.deliveryReceiver(delivery.getReceiver())
				.deliveryReceiverPhoneNumber(delivery.getReceiverPhoneNumber())
				.deliveryReceiverAddress(address.getAddress())
				.deliveryReceiverAddressDetail(address.getAddressDetail())
				.deliveryStatus(delivery.getStatus())
				.build();
	}

}