package com.artx.artx.delivery.entity;

import com.artx.artx.user.entity.User;
import com.artx.artx.delivery.type.DeliveryStatus;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.model.OrderCreate;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.Address;
import com.artx.artx.common.model.BaseEntity;
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
	@GenericGenerator(name = "delivery-id-generator", strategy = "com.artx.artx.common.util.generator.DeliveryGenerator")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Embedded
	private Address address;

	private String receiver;
	private String receiverPhoneNumber;

	private String company;
	private String trackingNumber;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	public static Delivery from(User seller, Order order, OrderCreate.Request request) {
		return Delivery.builder()
				.user(seller)
				.order(order)
				.receiver(request.getOrderDeliveryDetail().getDeliveryReceiver())
				.receiverPhoneNumber(request.getOrderDeliveryDetail().getDeliveryReceiverPhoneNumber())
				.address(
						Address.builder()
								.address(request.getOrderDeliveryDetail().getDeliveryReceiverAddress())
								.addressDetail(request.getOrderDeliveryDetail().getDeliveryReceiverAddressDetail())
								.build()
				)
				.status(DeliveryStatus.DELIVERY_CREATED)
				.build();
	}

	public boolean isCacnelable() {
		if(this.status == null || this.status == DeliveryStatus.DELIVERY_READY){
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_PAYMENT_CANCEL);
	}

	public void changeStatus(DeliveryStatus status) {
		this.status = status;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
