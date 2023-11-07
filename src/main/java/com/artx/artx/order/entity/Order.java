package com.artx.artx.order.entity;

import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderProduct> orderProducts;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	private String title;
	private long totalAmount;

	public void addOrderProduct(OrderProduct orderProduct){
		if(this.orderProducts == null){
			this.orderProducts = new ArrayList<>();
		}
		orderProduct.setOrder(this);
		this.orderProducts.add(orderProduct);
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void toOrderSuccess() {
		this.status = OrderStatus.ORDER_SUCCESS;
	}

	public void toOrderCancel() {
		this.status = OrderStatus.ORDER_SUCCESS;
	}

	public void toOrderFailure() {
		this.status = OrderStatus.ORDER_FAILURE;
	}
}
