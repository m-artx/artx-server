package com.artx.artx.cart.controller;

import com.artx.artx.cart.dto.CartResponse;
import com.artx.artx.cart.service.CartService;
import com.artx.artx.common.model.CommonOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@PostMapping("/{cartId}/products/{productId}")
	public ResponseEntity<CartResponse.Create> addToCart(@PathVariable Long cartId, @PathVariable Long productId) {
		return ResponseEntity.ok(cartService.addItem(cartId, productId));
	}

	@PatchMapping("/{cartId}/items/{itemId}/increase")
	public ResponseEntity<CartResponse.Create> increaseQuantity(
			@PathVariable Long cartId,
			@PathVariable Long itemId
	) {
		cartService.increaseQuantity(cartId, itemId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{cartId}/products/{productId}/decrease")
	public ResponseEntity<CartResponse.Create> decreaseQuantity(
			@PathVariable Long cartId,
			@PathVariable Long productId
	) {
		cartService.decreaseQuantity(cartId, productId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{cartId}")
	public ResponseEntity<CartResponse.Create> orderByCart(
			@PathVariable Long cartId,
			@RequestBody CommonOrder.Create request
	) {
		cartService.orderByCart(cartId, request);
		return ResponseEntity.ok().build();
	}

}
