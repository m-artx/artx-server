package com.artx.artx.order.controller;

import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성", description = "주문 정보와 함께 주문할 수 있다.")
	@PostMapping
	public ResponseEntity<CreateOrder.Response> create(@RequestBody CreateOrder.Request request){
		return ResponseEntity.ok(orderService.createOrder(request));
	}

}
