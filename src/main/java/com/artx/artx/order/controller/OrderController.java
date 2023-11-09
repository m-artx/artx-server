package com.artx.artx.order.controller;

import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.model.ReadOrder;
import com.artx.artx.order.service.OrderService;
import com.artx.artx.payment.model.CreatePayment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성", description = "주문 정보와 함께 주문할 수 있다.")
	@PostMapping
	public ResponseEntity<CreatePayment.ReadyResponse> createOrder(@RequestBody CreateOrder.Request request){
		return ResponseEntity.ok(orderService.createOrder(request));
	}

	@Operation(summary = "주문 조회", description = "주문 정보를 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<ReadOrder.ResponseAll>> readOrder(@RequestBody ReadOrder.Request request, Pageable pageable){
		return ResponseEntity.ok(orderService.readOrder(request, pageable));
	}

}
