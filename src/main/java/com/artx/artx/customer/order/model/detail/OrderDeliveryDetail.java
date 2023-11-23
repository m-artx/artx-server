package com.artx.artx.customer.order.model.detail;

import com.artx.artx.etc.model.Address;
import com.artx.artx.customer.delivery.entity.Delivery;
import com.artx.artx.customer.delivery.type.DeliveryStatus;
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
	private String deliveryTrackingNumber;

	@Nullable
	private Long deliveryFee;

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
				.deliveryTrackingNumber(delivery.getTrackingNumber())
				.deliveryStatus(delivery.getStatus())
				.build();
	}

}