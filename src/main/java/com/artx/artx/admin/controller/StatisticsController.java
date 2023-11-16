package com.artx.artx.admin.controller;

import com.artx.artx.admin.model.statistics.ReadOrderStatistics;
import com.artx.artx.admin.model.statistics.ReadUserStatistics;
import com.artx.artx.admin.service.StatisticsService;
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
public class StatisticsController {

	private final StatisticsService statisticsService;

	@GetMapping("/orders")
	public ResponseEntity<ReadOrderStatistics.StatusResponse> readAllOrderAndDeliveryStatus(){
		return ResponseEntity.ok(statisticsService.readAllOrderAndDeliveryStatusCounts());
	}

	@GetMapping("/orders/monthly-order-count")
	public ResponseEntity<ReadOrderStatistics.MonthlyResponse> readAllMonthlyOrderCounts(){
		return ResponseEntity.ok(statisticsService.readAllMonthlyOrderCounts());
	}

	@GetMapping("/orders/yearly-order-count")
	public ResponseEntity<ReadOrderStatistics.YearlyResponse> readAllYearlyOrderCounts(){
		return ResponseEntity.ok(statisticsService.readAllYearlyOrderCounts());
	}

	@GetMapping("/orders/daily-user-count")
	public ResponseEntity<ReadUserStatistics.Response> readAllDailyUserAndArtistCounts(){
		return ResponseEntity.ok(statisticsService.readAllDailyUserAndArtistCounts());
	}

}
