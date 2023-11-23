package com.artx.artx.artist.order.model;

import com.artx.artx.common.user.entity.User;
import com.artx.artx.customer.delivery.entity.Delivery;
import com.artx.artx.customer.order.entity.Order;
import com.artx.artx.customer.order.model.detail.OrderDeliveryDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.UUID;

public class ArtistOrderRead {

	@Builder
	@Getter
	public static class SummaryResponse {

		@Schema(description = "유저 고유 식별 번호", example = "fafe2100-e770-4cfc-aef7-960837b777df")
		private UUID userId;

		@Schema(description = "아이디", example = "artxlover")
		private String username;

		@Schema(description = "주문 고유 식별 번호", example = "ORD-20230101XXX")
		private String orderId;

		@Schema(description = "주문 총액", example = "490000")
		private Long orderTotalAmount;

		@Schema(description = "주문 등록 시간", example = "2023-01-01T10:00:30")
		private LocalDateTime orderCreatedAt;

		public static SummaryResponse of(Order order) {
			User user = order.getUser();
			long orderTotalAmount = order.getOrderProducts().stream().mapToLong(orderProduct -> orderProduct.getQuantity() * orderProduct.getProduct().getPrice()).sum();

			return SummaryResponse.builder()
					.userId(user.getUserId())
					.username(user.getUsername())
					.orderId(order.getId())
					.orderTotalAmount(orderTotalAmount)
					.orderCreatedAt(order.getCreatedAt())
					.build();
		}

	}

	@Getter
	@Builder
	public static class DetailResponse {

		@Schema(description = "주문 작품 정보", example = "")
		private Page<ArtistOrderProductDetail> orderProductDetail;

		@Schema(description = "", example = "")
		private OrderDeliveryDetail orderDeliveryDetail;

		public static DetailResponse of(Page<ArtistOrderProductDetail> artistOrderProductDetails, Delivery delivery) {
			return DetailResponse.builder()
					.orderProductDetail(artistOrderProductDetails)
					.orderDeliveryDetail(OrderDeliveryDetail.of(delivery))
					.build();
		}

	}

}
