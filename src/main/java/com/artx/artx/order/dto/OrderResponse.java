package com.artx.artx.order.dto;

import com.artx.artx.order.model.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class OrderResponse {

	@Builder
	@Getter
	public static class Create{
		private Long orderId;
		private Long totalPrice;
		private LocalDateTime orderDate;

		public static Create from(Order order){
			return OrderResponse.Create.builder()
					.orderId(order.getId())
					.totalPrice(order.getPrice().longValue())
					.orderDate(order.getCreatedAt())
					.build();
		}
	}
}
