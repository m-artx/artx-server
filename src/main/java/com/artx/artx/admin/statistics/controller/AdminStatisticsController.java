package com.artx.artx.admin.statistics.controller;

import com.artx.artx.admin.statistics.model.OrderStatisticsRead;
import com.artx.artx.admin.statistics.model.UserStatisticsRead;
import com.artx.artx.admin.statistics.service.AdminStatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin/statistics")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminStatisticsController {

	private final AdminStatisticsService statisticsService;

	@GetMapping("/orders")
	public ResponseEntity<OrderStatisticsRead.StatusResponse> readAllOrderAndDeliveryStatus(){
		return ResponseEntity.ok(statisticsService.readAllOrderAndDeliveryStatusCounts());
	}

	@GetMapping("/orders/monthly-order-count")
	public ResponseEntity<OrderStatisticsRead.MonthlyResponse> readAllMonthlyOrderCounts(){
		return ResponseEntity.ok(statisticsService.readAllMonthlyOrderCounts());
	}

	@GetMapping("/orders/yearly-order-count")
	public ResponseEntity<OrderStatisticsRead.YearlyResponse> readAllYearlyOrderCounts(){
		return ResponseEntity.ok(statisticsService.readAllYearlyOrderCounts());
	}

	@GetMapping("/orders/daily-user-count")
	public ResponseEntity<UserStatisticsRead.Response> readAllDailyUserAndArtistCounts(){
		return ResponseEntity.ok(statisticsService.readAllDailyUserAndArtistCounts());
	}

}
