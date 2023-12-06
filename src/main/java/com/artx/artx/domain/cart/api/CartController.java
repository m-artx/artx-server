package com.artx.artx.domain.cart.api;

import com.artx.artx.domain.auth.domain.UserDetails;
import com.artx.artx.domain.cart.dto.CartProductCreate;
import com.artx.artx.domain.cart.dto.CartProductDecrease;
import com.artx.artx.domain.cart.dto.CartProductDelete;
import com.artx.artx.domain.cart.dto.CartProductRead;
import com.artx.artx.domain.cart.application.CartService;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@Operation(summary = "장바구니 상품 추가", description = "상품을 장바구니에 추가할 수 있다.")
	@PostMapping
	public ResponseEntity<CartProductCreate.Response> addToCartProduct(
			@RequestBody CartProductCreate.Request request
	) {
		return ResponseEntity.ok(cartService.addProduct(getUserId(), request.getProductId()));
	}

	@Operation(summary = "장바구니 전체 조회", description = "장바구니에 있는 상품들을 전체 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<CartProductRead.Response> readAllCartItems(
			Pageable pageable
	) {
		return ResponseEntity.ok(cartService.readAllCarProductsByCartId(getUserId(), pageable));
	}

	@Operation(summary = "장바구니 선택 삭제", description = "장바구니에 있는 상품들을 전체 삭제할 수 있다.")
	@DeleteMapping
	public ResponseEntity<CartProductRead.Response> deleteCartItems(
			@RequestBody CartProductDelete.Request request
	) {
		cartService.deleteSelectedCartProducts(getUserId(), request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "장바구니 상품 수량 증가", description = "상품의 수량을 증가시킬 수 있다.")
	@PatchMapping("/increase")
	public ResponseEntity<Void> increaseQuantity(
			@RequestBody CartProductDecrease.Request request
	) {
		cartService.increaseProductQuantity(getUserId(), request.getProductId());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "장바구니 상품 수량 감소", description = "상품의 수량을 감소시킬 수 있다.")
	@PatchMapping("/decrease")
	public ResponseEntity<Void> decreaseQuantity(
			@RequestBody CartProductDecrease.Request request
	) {
		cartService.decreaseProductQuantity(getUserId(), request.getProductId());
		return ResponseEntity.ok().build();
	}

	public UUID getUserId() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userDetails.getUserId();

		} catch (ClassCastException e) {
			throw new BusinessException(ErrorCode.NEED_TO_CHECK_TOKEN);
		}
	}
}
