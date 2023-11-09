package com.artx.artx.order.entity;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.Address;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.order.model.CreateOrder;
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

	public static Delivery from(CreateOrder.Request request) {
		return Delivery.builder()
				.receiver(request.getDeliveryDetail().getDeliveryReceiver())
				.receiverPhoneNumber(request.getDeliveryDetail().getDeliveryReceiverPhoneNumber())
				.address(
						Address.builder()
								.address(request.getDeliveryDetail().getDeliveryReceiverAddress())
								.addressDetail(request.getDeliveryDetail().getDeliveryReceiverAddressDetail())
								.build()
				)
				.status(DeliveryStatus.READY)
				.build();
	}

	public boolean isCacnelable() {
		if(this.status == DeliveryStatus.READY){
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_PAYMENT_CANCEL);
	}
}
