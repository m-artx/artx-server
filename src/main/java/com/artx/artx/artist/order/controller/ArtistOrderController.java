package com.artx.artx.artist.order.controller;

import com.artx.artx.artist.order.model.ArtistOrderRead;
import com.artx.artx.artist.order.service.ArtistOrderService;
import com.artx.artx.common.auth.model.UserDetails;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "작가 주문 관리")
@RestController
@RequestMapping("/api/artist/orders")
@RequiredArgsConstructor
@Secured("ROLE_ARTIST")
public class ArtistOrderController {

	private final ArtistOrderService artistOrderService;

	@GetMapping
	public ResponseEntity<Page<ArtistOrderRead.SummaryResponse>> readAllOrders(
			Pageable pageable
	) {
		return ResponseEntity.ok(artistOrderService.readAllOrders(getUserId(), pageable));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<ArtistOrderRead.DetailResponse> readOrderDetail(
			@PathVariable String orderId,
			Pageable pageable
	) {
		return ResponseEntity.ok(artistOrderService.readOrderDetail(getUserId(), orderId, pageable));
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
