package com.artx.artx.order.entity;

import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.order.type.OrderStatus;
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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderProduct> orderProducts;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	private long price;

	public void addOrderProduct(OrderProduct orderProduct){
		if(this.orderProducts == null){
			this.orderProducts = new ArrayList<>();
		}
		orderProduct.setOrder(this);
		this.orderProducts.add(orderProduct);
	}

}
