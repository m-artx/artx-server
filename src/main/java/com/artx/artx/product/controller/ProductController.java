package com.artx.artx.product.controller;

import com.artx.artx.product.dto.ProductRequest;
import com.artx.artx.product.dto.ProductResponse;
import com.artx.artx.product.service.ProductService;
import com.artx.artx.product.type.FilterType;
import com.artx.artx.product.type.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	//새로운 작품 등록
	@PostMapping("/new")
	public ResponseEntity<ProductResponse.Create> create(@RequestPart ProductRequest.Create request, @RequestPart List<MultipartFile> files){
		return ResponseEntity.ok(productService.createProduct(request, files));
	}

	//특정 유저 작품 전체 조회(페이징)
	@GetMapping("/search")
	public ResponseEntity<Page<ProductResponse.ReadAll>> searchProducts(@RequestParam SearchType type, String name, Pageable pageable){
		return ResponseEntity.ok(productService.searchProducts(type, name , pageable));
	}

	//메인 페이지 작품 조회
	@GetMapping("/main")
	public ResponseEntity<List<ProductResponse.ReadAll>> mainPageProducts(@RequestParam FilterType type){
		return ResponseEntity.ok(productService.mainPageProducts(type));
	}

}
