package com.artx.artx.order.model;

import com.artx.artx.common.model.Address;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.common.model.CommonOrder;
import com.artx.artx.order.type.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Address address;

	private String receiver;
	private String receiverPhoneNumber;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	public static Delivery from(CommonOrder.Create request) {
		return Delivery.builder()
				.receiver(request.getDeliveryData().getReceiver())
				.receiverPhoneNumber(request.getDeliveryData().getPhoneNumber())
				.address(
						Address.builder()
								.address(request.getDeliveryData().getAddress())
								.addressDetail(request.getDeliveryData().getAddressDetail())
								.build()
				)
				.status(DeliveryStatus.READY)
				.build();
	}
}
