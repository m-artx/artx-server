package com.artx.artx.cart.controller;

import com.artx.artx.cart.dto.CreateCartItem;
import com.artx.artx.cart.dto.DeleteCartItem;
import com.artx.artx.cart.dto.ReadCartItem;
import com.artx.artx.cart.service.CartService;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.payment.model.CreatePayment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@Operation(summary = "장바구니 상품 추가", description = "상품을 장바구니에 추가할 수 있다.")
	@PostMapping("/{cartId}/products/{productId}")
	public ResponseEntity<CreateCartItem.Response> addToCart(@PathVariable Long cartId, @PathVariable Long productId) {
		return ResponseEntity.ok(cartService.addProduct(cartId, productId));
	}

	@Operation(summary = "장바구니 상품 수량 증가", description = "상품의 수량을 증가시킬 수 있다.")
	@PatchMapping("/{cartId}/products/{productId}/increase")
	public ResponseEntity<Void> increaseQuantity(
			@PathVariable Long cartId,
			@PathVariable Long productId
	) {
		cartService.increaseProductQuantity(cartId, productId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "장바구니 상품 수량 감소", description = "상품의 수량을 감소시킬 수 있다.")
	@PatchMapping("/{cartId}/products/{productId}/decrease")
	public ResponseEntity<Void> decreaseQuantity(
			@PathVariable Long cartId,
			@PathVariable Long productId
	) {
		cartService.decreaseProductQuantity(cartId, productId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "장바구니 상품 주문", description = "장바구니에 있는 상품들을 주문할 수 있다.")
	@PostMapping("/{cartId}")
	public ResponseEntity<CreatePayment.ReadyResponse> orderByCart(
			@PathVariable Long cartId,
			@RequestBody CreateOrder.Request request
	) {
		return ResponseEntity.ok(cartService.createOrder(cartId, request));
	}

	@Operation(summary = "장바구니 전체 조회", description = "장바구니에 있는 상품들을 전체 조회할 수 있다.")
	@GetMapping("/{cartId}")
	public ResponseEntity<ReadCartItem.Response> readAllCartItems(
			@PathVariable Long cartId,
			Pageable pageable
	) {
		return ResponseEntity.ok(cartService.readAllCarItemsByCartId(cartId, pageable));
	}

	@Operation(summary = "장바구니 전체 삭제", description = "장바구니에 있는 상품들을 전체 삭제할 수 있다.")
	@DeleteMapping("/{cartId}/all")
	public ResponseEntity<ReadCartItem.Response> deleteAllCartItems(
			@PathVariable Long cartId
	) {
		cartService.deleteAllCarItems(cartId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "장바구니 선택 삭제", description = "장바구니에 있는 상품들을 전체 삭제할 수 있다.")
	@DeleteMapping("/{cartId}")
	public ResponseEntity<ReadCartItem.Response> deleteCartItems(
			@PathVariable Long cartId,
			@RequestBody DeleteCartItem.Request request
	) {
		cartService.deleteSelectedCartItems(cartId, request);
		return ResponseEntity.ok().build();
	}

}
