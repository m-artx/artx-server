package com.artx.artx.product.controller;

import com.artx.artx.product.model.ProductCategoryRead;
import com.artx.artx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "작품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class ProductCategoryController {

	private final ProductService productService;

	@Operation(summary = "전체 카테고리 조회", description = "모든 카테고리의 이름, 상세 설명, 대표 이미지를 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<List<ProductCategoryRead.SummaryResponse>> readCategories(){
		return ResponseEntity.ok(productService.readCategories());
	}

}
