package com.artx.artx.admin.model.statistics;

import com.artx.artx.delivery.type.DeliveryStatus;
import com.artx.artx.order.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

public class OrderStatisticsRead {

	@Getter
	@Builder
	public static class StatusResponse {
		private long orderSuccessCounts;
		private long orderApprovalCounts;
		private long deliveryInProgressCounts;
		private long deliveryCompletedCounts;

		public static StatusResponse from(Map<OrderStatus, Long> orderMap, Map<DeliveryStatus, Long> deliveryMap) {

			return StatusResponse.builder()
					.orderSuccessCounts(Objects.isNull(orderMap.get(OrderStatus.ORDER_SUCCESS)) ? 0 : orderMap.get(OrderStatus.ORDER_SUCCESS))
					.orderApprovalCounts(Objects.isNull(orderMap.get(OrderStatus.ORDER_APPROVAL)) ? 0 : orderMap.get(OrderStatus.ORDER_APPROVAL))
					.deliveryInProgressCounts(Objects.isNull(deliveryMap.get(DeliveryStatus.DELIVERY_IN_PROGRESS)) ? 0 : deliveryMap.get(DeliveryStatus.DELIVERY_IN_PROGRESS))
					.deliveryCompletedCounts(Objects.isNull(deliveryMap.get(DeliveryStatus.DELIVERY_COMPLETED)) ? 0 : deliveryMap.get(DeliveryStatus.DELIVERY_COMPLETED))
					.build();
		}
	}

	@Getter
	@Builder
	public static class MonthlyResponse {

		private long totalOrderCounts;
		private long increaseCounts;


		public static MonthlyResponse from(Long previousMonthTotalOrderCounts, Long presentMonthTotalOrderCounts) {
			return MonthlyResponse.builder()
					.totalOrderCounts(presentMonthTotalOrderCounts)
					.increaseCounts(presentMonthTotalOrderCounts - previousMonthTotalOrderCounts)
					.build();
		}
	}

	@Getter
	@Builder
	public static class YearlyResponse {

		private long totalOrderCounts;
		private long increaseCounts;

		public static YearlyResponse from(Long previousYearTotalOrderCounts, Long presentYearTotalOrderCounts) {
			if(Objects.isNull(previousYearTotalOrderCounts)){
				previousYearTotalOrderCounts = 0L;
			}
			return YearlyResponse.builder()
					.totalOrderCounts(presentYearTotalOrderCounts)
					.increaseCounts(presentYearTotalOrderCounts - previousYearTotalOrderCounts)
					.build();
		}
	}

}
