package com.artx.artx.order.entity;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
	@GeneratedValue(generator = "order-id-generator")
	@GenericGenerator(name = "order-id-generator", strategy = "com.artx.artx.common.util.OrderGenerator")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

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

	public void toOrderSuccess() {
		this.status = OrderStatus.ORDER_SUCCESS;
	}

	public void toOrderCancel() {
		this.status = OrderStatus.ORDER_SUCCESS;
	}

	public void toOrderFailure() {
		this.status = OrderStatus.ORDER_FAILURE;
	}

	public boolean isCancelable() {
		if(this.status == OrderStatus.ORDER_READY || this.status == OrderStatus.ORDER_SUCCESS){
			return true;
		}
		throw new BusinessException(ErrorCode.CAN_NOT_ORDER_CANCEL);
	}

	public void setDelivery(Delivery delivery){
		this.delivery = delivery;
	}
}
