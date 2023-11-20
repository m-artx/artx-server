package com.artx.artx.artist.controller;

import com.artx.artx.artist.service.ArtistOrderService;
import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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

	//TODO: 작가 주문 전체 조회

//	public ResponseEntity<Void> readAllOrders(){
//		artistOrderService.readAllOrders(getUserId());
//	}

	public UUID getUserId(){
		try{
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userDetails.getUserId();

		} catch (ClassCastException e){
			throw new BusinessException(ErrorCode.NEED_TO_CHECK_TOKEN);
		}

	}

}
