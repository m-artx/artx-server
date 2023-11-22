package com.artx.artx.order.model.detail;

import com.artx.artx.common.model.Address;
import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.delivery.type.DeliveryStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDeliveryDetail {

	private String deliveryId;

	@NotBlank
	private String deliveryReceiver;

	@NotBlank
	private String deliveryReceiverPhoneNumber;

	@NotBlank
	private String deliveryReceiverAddress;

	@NotBlank
	private String deliveryReceiverAddressDetail;

	@Nullable
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