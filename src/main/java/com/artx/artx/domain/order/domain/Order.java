package com.artx.artx.domain.order.domain;

import com.artx.artx.domain.order.dto.OrderCreate;
import com.artx.artx.domain.user.domain.User;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import com.artx.artx.domain.model.Address;
import com.artx.artx.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(generator = "order-id-generator")
	@GenericGenerator(name = "order-id-generator", strategy = "com.artx.artx.global.common.util.generator.OrderGenerator")
	private String id;

	@Column(nullable = false)
	private String receiver;

	@Column(nullable = false)
	private String phoneNumber;

	@Embedded
	@Column(nullable = false)
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@Column(nullable = false)
	List<OrderProduct> orderProducts = new ArrayList<>();

	public static Order from(User user, OrderCreate.Request request) {
		return Order.builder()
				.user(user)
				.phoneNumber(request.getOrderDeliveryDetail().getDeliveryReceiverPhoneNumber())
				.receiver(request.getOrderDeliveryDetail().getDeliveryReceiver())
				.address(
						Address.builder()
								.address(request.getOrderDeliveryDetail().getDeliveryReceiverAddress())
								.addressDetail(request.getOrderDeliveryDetail().getDeliveryReceiverAddressDetail())
								.build()
				)
				.status(OrderStatus.ORDER_READY)
				.build();
	}

	public void addOrderProduct(OrderProduct orderProduct) {
		if (this.orderProducts == null) {
			this.orderProducts = new ArrayList<>();
		}
		orderProduct.setOrder(this);
		this.orderProducts.add(orderProduct);
	}

	public void processOrderSuccess() {
		this.status = OrderStatus.ORDER_SUCCESS;
	}

	public void processOrderCancel() {
		this.status = OrderStatus.ORDER_SUCCESS;
	}

	public void processOrderFailure() {
		this.status = OrderStatus.ORDER_FAILURE;
	}

	public boolean isCancelable() {
		if (this.status == OrderStatus.ORDER_READY || this.status == OrderStatus.ORDER_SUCCESS) {
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_ORDER_CANCEL);
	}

	public String generateOrderTitle() {
		String representativeProductName = this.orderProducts.get(0).getProduct().getTitle();
		Integer orderProductsSize = this.orderProducts.size();

		return orderProductsSize > 1 ?
				representativeProductName + " 외 " + (orderProductsSize - 1) + "개의 작품" :
				representativeProductName;
	}

	public Long generateTotalAmount() {
		return orderProducts.stream().mapToLong(orderProduct -> orderProduct.getQuantity() * orderProduct.getProduct().getPrice()).sum();
	}

//	public void setDelivery(Delivery delivery) {
//		this.delivery = delivery;
//	}
}
