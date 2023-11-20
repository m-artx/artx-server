package com.artx.artx.admin.service;

import com.artx.artx.admin.model.statistics.OrderStatisticsRead;
import com.artx.artx.admin.model.statistics.UserStatisticsRead;
import com.artx.artx.delivery.repository.DeliveryRepository;
import com.artx.artx.delivery.type.DeliveryStatus;
import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.user.repository.UserRepository;
import com.artx.artx.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminStatisticsService {

	private final UserRepository userRepository;
	private final DeliveryRepository deliveryRepository;
	private final OrderRepository orderRepository;

	public OrderStatisticsRead.StatusResponse readAllOrderAndDeliveryStatusCounts() {
		return OrderStatisticsRead.StatusResponse.from(readAllOrderStatusCounts(), readAllDeliveryStatusCounts());

	}

	public Map<OrderStatus, Long> readAllOrderStatusCounts() {

		List<Object[]> statusCounts = orderRepository.countAllOrderStatus();
		Map<OrderStatus, Long> map = new HashMap<>();

		for (Object[] statusCount : statusCounts) {
			map.put((OrderStatus) statusCount[0], (Long) statusCount[1]);
		}

		return map;
	}

	public Map<DeliveryStatus, Long> readAllDeliveryStatusCounts() {
		List<Object[]> statusCounts = deliveryRepository.getAllDeliveryStatusCounts();
		Map<DeliveryStatus, Long> map = new HashMap<>();

		for (Object[] statusCount : statusCounts) {
			map.put((DeliveryStatus) statusCount[0], (Long) statusCount[1]);
		}
		return map;
	}

	public OrderStatisticsRead.YearlyResponse readAllYearlyOrderCounts() {

		LocalDateTime startDateTime = LocalDateTime.now().minusYears(1).withDayOfYear(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusYears(1).withDayOfYear(1).minusDays(1);

		List<Object[]> orderCounts = orderRepository.countAllYearlyOrder(startDateTime, endDateTime);

		Map<String, Long> map = new HashMap<>();

		if (orderCounts.size() == 2) {
			map.put("previousYear", (Long) orderCounts.get(0)[1]);
			map.put("presentYear", (Long) orderCounts.get(1)[1]);
		}

		if (orderCounts.size() == 1) {
			map.put("presentYear", (Long) orderCounts.get(0)[1]);
		}

		Long previousYearTotalOrderCounts = map.get("previousYear");
		Long presentYearTotalOrderCounts = map.get("presentYear");

		return OrderStatisticsRead.YearlyResponse.from(previousYearTotalOrderCounts, presentYearTotalOrderCounts);

	}


	public OrderStatisticsRead.MonthlyResponse readAllMonthlyOrderCounts() {

		LocalDateTime startDateTime = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusDays(1);

		List<Object[]> orderCounts = orderRepository.countAllMontlyOrder(startDateTime, endDateTime);

		return OrderStatisticsRead.MonthlyResponse.from((Long) orderCounts.get(0)[1], (Long) orderCounts.get(1)[1]);
	}

	public UserStatisticsRead.Response readAllDailyUserAndArtistCounts() {
		List<Object[]> objects = userRepository.readAllDailyUserAndArtistCounts();

		Map<UserRole, Long> map = new HashMap<>();
		for (Object[] object : objects) {
			UserRole role = (UserRole) object[0];
			Long count = (Long) object[1];
			map.put(role, count);
		}

		return UserStatisticsRead.Response.from(map);
	}

}
