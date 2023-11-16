package com.artx.artx.admin.service;

import com.artx.artx.admin.model.statistics.ReadOrderStatistics;
import com.artx.artx.admin.model.statistics.ReadUserStatistics;
import com.artx.artx.delivery.service.DeliveryService;
import com.artx.artx.delivery.type.DeliveryStatus;
import com.artx.artx.order.service.OrderService;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.user.service.UserService;
import com.artx.artx.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {

	private final UserService userService;
	private final OrderService orderService;
	private final DeliveryService deliveryService;

	public ReadOrderStatistics.StatusResponse readAllOrderAndDeliveryStatusCounts() {
		return ReadOrderStatistics.StatusResponse.from(readAllOrderStatusCounts(), readAllDeliveryStatusCounts());

	}

	public Map<OrderStatus, Long> readAllOrderStatusCounts() {
		return orderService.readAllOrderStatusCounts();
	}

	public Map<DeliveryStatus, Long> readAllDeliveryStatusCounts() {
		return deliveryService.getAllDeliveryStatusCounts();
	}


	public ReadOrderStatistics.MonthlyResponse readAllMonthlyOrderCounts() {
		Map<String, Long> map = orderService.readAllMonthlyOrderCounts();

		Long previousMonthTotalOrderCounts = map.get("previousMonth");
		Long presentMonthTotalOrderCounts = map.get("presentMonth");

		return ReadOrderStatistics.MonthlyResponse.from(previousMonthTotalOrderCounts, presentMonthTotalOrderCounts);
	}

	public ReadOrderStatistics.YearlyResponse readAllYearlyOrderCounts() {
		Map<String, Long> map = orderService.readAllYearlyOrderCounts();

		Long previousYearTotalOrderCounts = map.get("previousYear");
		Long presentYearTotalOrderCounts = map.get("presentYear");

		return ReadOrderStatistics.YearlyResponse.from(previousYearTotalOrderCounts, presentYearTotalOrderCounts);
	}

	public ReadUserStatistics.Response readAllDailyUserAndArtistCounts() {
		Map<UserRole, Long> map = userService.readAllDailyUserAndArtistCounts();
		return ReadUserStatistics.Response.from(map);
	}
}
