package com.artx.artx.product.controller;

import com.artx.artx.product.type.ProductCategoryType;
import com.artx.artx.product.type.ProductSearchFilter;
import com.artx.artx.product.type.ProductSearchType;
import com.artx.artx.product.model.ProductRead;
import com.artx.artx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "작품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "작품 조회", description = "카테고리와 작가명 또는 제목으로 작품을 전체 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<ProductRead.SummaryResponse>> searchProducts(
			@Nullable @RequestParam ProductCategoryType category,
			@Nullable @RequestParam ProductSearchType type,
			@Nullable @RequestParam String name,
			Pageable pageable) {
		return ResponseEntity.ok(productService.readAllProducts(category, type, name, pageable));
	}

	@Operation(summary = "작품 상세 페이지 조회", description = "특정 작품의 상세 페이지를 조회할 수 있다.")
	@GetMapping("/{productId}")
	public ResponseEntity<ProductRead.DetailResponse> readProductDetail(
			@PathVariable Long productId
	) {
		return ResponseEntity.ok(productService.readProductDetail(productId));
	}

	@Operation(summary = "메인 페이지 조회", description = "등록순 및 인기순으로 작품 10개를 조회할 수 있다.")
	@GetMapping("/main")
	public ResponseEntity<List<ProductRead.SummaryResponse>> mainPageProducts(
			@RequestParam ProductSearchFilter type
	) {
		return ResponseEntity.ok(productService.readMainPageProducts(type));
	}

}
