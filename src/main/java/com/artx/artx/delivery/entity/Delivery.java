package com.artx.artx.delivery.entity;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.Address;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.delivery.type.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(generator = "delivery-id-generator")
	@GenericGenerator(name = "delivery-id-generator", strategy = "com.artx.artx.common.util.DeliveryGenerator")
	private String id;

	@Embedded
	private Address address;

	private String receiver;
	private String receiverPhoneNumber;

	private String company;
	private String trackingNumber;

	private Long fee;

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
				.status(DeliveryStatus.DELIVERY_READY)
				.build();
	}

	public boolean isCacnelable() {
		if(this.status == DeliveryStatus.DELIVERY_READY){
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_PAYMENT_CANCEL);
	}

}
