package com.artx.artx.order.model;

import com.artx.artx.order.entity.Order;
import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.order.model.detail.OrderProductDetail;
import com.artx.artx.order.model.summary.OrderProductSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderRead {

	@Getter
	public static class Request {
		private UUID userId;
	}

	@Builder
	@Getter
	public static class SummaryResponse {
		@Schema(description = "주문 고유 식별 번호", example = "ORD-20230101XXX")
		private String orderId;

		@Schema(description = "주문 총 금액", example = "100000")
		private Long orderTotalAmount;

		@Schema(description = "주문 작품 정보", example = "")
		private List<OrderProductSummary> orderProductSummary;

		@Schema(description = "주문 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;

		public static SummaryResponse of(Order order) {
			List<OrderProduct> orderProducts = order.getOrderProducts();
			return SummaryResponse.builder()
					.orderId(order.getId())
					.orderTotalAmount(order.generateTotalAmount())
					.orderProductSummary(
							orderProducts.stream()
									.map(OrderProductSummary::of)
									.collect(Collectors.toList())
					)
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}
	}

	@Builder
	@Getter
	public static class DetailResponse {
		@Schema(description = "주문 고유 식별 번호", example = "ORD-20230101XXX")
		private String orderId;

		@Schema(description = "주문명", example = "개화 및 3개의 작품")
		private String orderTitle;

		@Schema(description = "주문 총 금액", example = "100000")
		private Long orderTotalAmount;

		@Schema(description = "주문 작품 정보", example = "")
		private List<OrderProductDetail> orderProductDetails;

		@Schema(description = "주문 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;

		public static DetailResponse of(Order order) {
			List<OrderProduct> orderProducts = order.getOrderProducts();
			return DetailResponse.builder()
					.orderId(order.getId())
					.orderTitle(order.generateOrderTitle())
					.orderTotalAmount(order.generateTotalAmount())
					.orderProductDetails(
							orderProducts.stream()
									.map(OrderProductDetail::of)
									.collect(Collectors.toList())
					)
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}
	}

}
