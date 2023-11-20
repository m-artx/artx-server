package com.artx.artx.artist.model;

import com.artx.artx.order.model.detail.OrderDeliveryDetail;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.order.model.detail.OrderProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArtistOrderRead {
	@Builder
	@Getter
	public static class ResponseAll {

		@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;
		@Schema(description = "아이디", example = "artxlover")
		private String username;
		@Schema(description = "닉네임", example = "김작가")
		private String userNickname;
		@Schema(description = "휴대폰 번호", example = "010-1234-5678")
		private String userPhoneNumber;

		@Schema(description = "주문 고유 식별 번호", example = "ORD-20230101XXX")
		private String orderId;
		@Schema(description = "주문명", example = "개화 및 3개의 작품")
		private String orderTitle;
		@Schema(description = "주문 총 금액", example = "100000")
		private Long orderTotalAmount;
		@Schema(description = "주문 작품 정보", example = "")
		private List<OrderProductDetail> orderDetails;
		@Schema(description = "배송 상세 정보", example = "")
		private OrderDeliveryDetail deliveryDetail;
		@Schema(description = "주문 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;

		public static ResponseAll of(Order order) {
			List<OrderProduct> orderProducts = order.getOrderProducts();

			return ResponseAll.builder()
					.orderId(order.getId())
					.orderTitle(order.generateOrderTitle())
					.orderTotalAmount(order.generateTotalAmount())
					.orderDetails(
							orderProducts.stream()
									.map(OrderProductDetail::of)
									.collect(Collectors.toList())
					)
//					.deliveryDetail(OrderDeliveryDetail.of(order.getDelivery()))
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}

	}
}
