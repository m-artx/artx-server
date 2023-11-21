package com.artx.artx.order.model;

import com.artx.artx.order.entity.Order;
import com.artx.artx.order.model.detail.OrderDeliveryDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class OrderCreate {

	@Getter
	public static class Request {
		@Schema(description = "주문 상세 정보", example = "")
		private List<OrderProductIdAndQuantity> orderProductDetails;
		@Schema(description = "배송 상세 정보", example = "")
		private OrderDeliveryDetail orderDeliveryDetail;
	}

	@Builder
	@Getter
	public static class Response {
		@Schema(description = "주문 고유 식별 번호", example = "1")
		private String orderId;
		@Schema(description = "주문명", example = "개화 및 3개의 작품")
		private String orderTitle;
		@Schema(description = "주문 총 금액", example = "100000")
		private Long orderTotalAmount;
		@Schema(description = "주문 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;

		public static Response of(Order order) {
			return Response.builder()
					.orderId(order.getId())
					.orderTitle(order.generateOrderTitle())
					.orderTotalAmount(order.generateTotalAmount())
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}
	}

}


