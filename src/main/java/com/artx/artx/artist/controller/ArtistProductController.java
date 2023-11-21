package com.artx.artx.artist.controller;

import com.artx.artx.artist.model.ArtistProductDelete;
import com.artx.artx.artist.service.ArtistProductService;
import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.product.model.ProductCreate;
import com.artx.artx.product.model.ProductRead;
import com.artx.artx.product.model.ProductUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "작가 상품 관리")
@RestController
@RequestMapping("/api/artist/products")
@RequiredArgsConstructor
@Secured("ROLE_ARTIST")
public class ArtistProductController {

	private final ArtistProductService artistProductService;

	@Operation(summary = "작품 등록", description = "작가는 새로운 작품을 등록할 수 있다.")
	@PostMapping
	public ResponseEntity<ProductCreate.Response> create(
			@Valid @RequestBody ProductCreate.Request request
	){
		return ResponseEntity.ok(artistProductService.createProduct(getUserId(), request));
	}

	@Operation(summary = "작품 수정", description = "작가는 새로운 작품을 등록할 수 있다.")
	@PatchMapping("/{productId}")
	public ResponseEntity<ProductUpdate.Response> update(
			@Valid @RequestBody ProductUpdate.Request request,
			@PathVariable Long productId
	){
		return ResponseEntity.ok(artistProductService.updateProduct(productId, request));
	}

	@Operation(summary = "작품 삭제", description = "작품을 삭제할 수 있다.")
	@DeleteMapping("/{productId}")
	public ResponseEntity<Page<ProductRead.SummaryResponse>> deleteProduct(@PathVariable Long productId){
		artistProductService.deleteProduct(productId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "작품 선택 삭제", description = "작품을 선택 삭제할 수 있다.")
	@DeleteMapping
	public ResponseEntity<Page<ProductRead.SummaryResponse>> deleteAllProducts(@RequestBody ArtistProductDelete.Request request){
		artistProductService.deleteSelectedProducts(request);
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
