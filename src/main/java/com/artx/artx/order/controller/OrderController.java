package com.artx.artx.order.controller;

import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.order.model.OrderCreate;
import com.artx.artx.order.model.OrderRead;
import com.artx.artx.order.service.OrderService;
import com.artx.artx.payment.model.PaymentCancel;
import com.artx.artx.payment.model.PaymentCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.UUID;

@Tag(name = "주문")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성", description = "주문 정보와 함께 주문할 수 있다.")
	@PostMapping
	public ResponseEntity<PaymentCreate.Response> createOrder(@RequestBody OrderCreate.Request request){
		return ResponseEntity.ok(orderService.createOrder(request));
	}

	@Operation(summary = "주문 조회", description = "주문 정보를 조회할 수 있다.")
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderRead.DetailResponse> readOrder(
			@PathVariable String orderId
	){
		return ResponseEntity.ok(orderService.readOrders(getUserId(), orderId));
	}

	@Operation(summary = "주문 전체 조회", description = "주문 전체 정보를 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<AbstractMap.SimpleEntry<LocalDate, OrderRead.SummaryResponse>>> readOrder(
			Pageable pageable
	){
		return ResponseEntity.ok(orderService.readAllOrders(getUserId(), pageable));
	}

	@Operation(summary = "주문 취소", description = "주문을 취소할 수 있다.")
	@PatchMapping("/{orderId}/cancel")
	public ResponseEntity<PaymentCancel.Response> cancelOrder(@PathVariable String orderId){
		return ResponseEntity.ok(orderService.cancelOrder(orderId));
	}

	public UUID getUserId(){
		try{
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userDetails.getUserId();

		} catch (ClassCastException e){
			throw new BusinessException(ErrorCode.NEED_TO_CHECK_TOKEN);
		}

	}

}
