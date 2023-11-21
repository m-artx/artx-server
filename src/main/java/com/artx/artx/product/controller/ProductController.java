package com.artx.artx.product.controller;

<<<<<<< HEAD
import com.artx.artx.auth.model.UserDetails;
import com.artx.artx.product.model.ProductCategoryRead;
=======
>>>>>>> Feature/ARTX-116
import com.artx.artx.product.model.ProductRead;
import com.artx.artx.product.service.ProductService;
import com.artx.artx.product.type.Category;
import com.artx.artx.product.type.Filter;
import com.artx.artx.product.type.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "작품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "작품 조회", description = "카테고리와 작가명 또는 제목으로 작품을 전체 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<ProductRead.SummaryResponse>> searchProducts(
			@Nullable @RequestParam Category category,
			@Nullable @RequestParam Type type,
			@Nullable @RequestParam String name,
			Pageable pageable) {
		return ResponseEntity.ok(productService.readAllProducts(category, type, name, pageable));
	}

	@Operation(summary = "작품 상세 페이지 조회", description = "특정 작품의 상세 페이지를 조회할 수 있다.")
	@GetMapping("/{productId}")
	public ResponseEntity<ProductRead.DetailResponse> readProductDetail(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.readProductDetail(productId));
	}

	@Operation(summary = "메인 페이지 조회", description = "등록순 및 인기순으로 작품 10개를 조회할 수 있다.")
	@GetMapping("/main")
	public ResponseEntity<List<ProductRead.SummaryResponse>> mainPageProducts(@RequestParam Filter type) {
		return ResponseEntity.ok(productService.readMainPageProducts(type));
	}

}
