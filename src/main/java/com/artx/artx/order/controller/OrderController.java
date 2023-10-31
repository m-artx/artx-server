package com.artx.artx.order.controller;

import com.artx.artx.common.model.CommonOrder;
import com.artx.artx.order.dto.OrderResponse;
import com.artx.artx.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponse.Create> create(@RequestBody CommonOrder.Create request){
		return ResponseEntity.ok(orderService.createOrder(request));
	}

}
