package com.artx.artx.order.model;

import com.artx.artx.delivery.model.DeliveryDetail;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadOrder {

	@Getter
	public static class Request{
		private UUID userId;
	}

	@Builder
	@Getter
	public static class Response {

		@Schema(description = "주문 고유 식별 번호", nullable = false, example = "ORD-20230101XXX")
		private String orderId;

		@Schema(description = "주문명", nullable = false, example = "개화 및 3개의 작품")
		private String orderTitle;

		@Schema(description = "주문 총 금액", nullable = false, example = "100000")
		private Long orderTotalAmount;

		@Schema(description = "주문 작품 정보", nullable = false, example = "")
		private List<OrderDetail> orderDetails;

		@Schema(description = "배송 상세 정보", nullable = false, example = "")
		private DeliveryDetail deliveryDetail;

		@Schema(description = "주문 등록 시간", nullable = false, example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;


		public static Response from(Order order) {
			List<OrderProduct> orderProducts = order.getOrderProducts();

			return Response.builder()
					.orderId(order.getId())
					.orderTitle(order.generateOrderTitle())
					.orderTotalAmount(order.generateTotalAmount())
					.orderDetails(
							orderProducts.stream()
									.map(OrderDetail::from)
									.collect(Collectors.toList())
					)
					.deliveryDetail(DeliveryDetail.from(order.getDelivery()))
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}
	}
}
