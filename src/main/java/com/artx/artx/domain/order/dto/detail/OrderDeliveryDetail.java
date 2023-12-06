package com.artx.artx.domain.order.dto.detail;

import com.artx.artx.domain.model.Address;
import com.artx.artx.domain.delivery.domain.Delivery;
import com.artx.artx.domain.delivery.domain.DeliveryStatus;
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