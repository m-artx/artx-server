package com.artx.artx.artist.controller;

import com.artx.artx.artist.service.ArtistProductService;
import com.artx.artx.artist.model.ArtistProductDelete;
import com.artx.artx.product.model.ProductCreate;
import com.artx.artx.product.model.ProductRead;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
			@RequestPart ProductCreate.Request request,
			@Nullable @RequestPart List<MultipartFile> files
	){
		return ResponseEntity.ok(artistProductService.createProduct(request, files));
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
		artistProductService.deleteAllProducts(request);
		return ResponseEntity.ok().build();
	}

}