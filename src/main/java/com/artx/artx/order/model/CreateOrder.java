package com.artx.artx.order.model;

import com.artx.artx.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CreateOrder {

	@Getter
	public static class Request {
		@Schema(description = "유저 고유 식별 번호", nullable = false, example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "주문 상세 정보", nullable = false, example = "")
		private List<OrderDetail> orderDetails;
		@Schema(description = "배송 상세 정보", nullable = false, example = "")
		private DeliveryDetail deliveryDetail;
	}

	@Builder
	@Getter
	public static class Response {
		@Schema(description = "유저 고유 식별 번호", nullable = false, example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "주문 고유 식별 번호", nullable = false, example = "1")
		private Long orderId;
		@Schema(description = "주문명", nullable = false, example = "개화 및 3개의 작품")
		private String orderTitle;
		@Schema(description = "주문 총 금액", nullable = false, example = "100000")
		private Long orderTotalAmount;
		@Schema(description = "주문 등록 시간", nullable = false, example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;

		public static Response from(Order order) {
			String representativeProductName = order.getOrderProducts().get(0).getProduct().getTitle();
			Integer orderProductsSize = order.getOrderProducts().size();

			String orderTitle = orderProductsSize > 0 ?
					representativeProductName + " 외 " + (orderProductsSize - 1) +"개의 작품" :
					representativeProductName;

			return Response.builder()
					.orderId(order.getId())
					.orderTitle(orderTitle)
					.orderTotalAmount(order.getTotalAmount())
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}
	}

	@Getter
	public static class OrderDetail {
		private Long productId;
		private Long productQuantity;
	}

	@Getter
	public static class DeliveryDetail {
		private String deliveryReceiver;
		private String deliveryReceiverPhoneNumber;
		private String deliveryReceiveraddress;
		private String deliveryReceiverAddressDetail;
	}
}

