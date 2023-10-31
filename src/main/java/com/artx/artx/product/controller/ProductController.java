package com.artx.artx.product.controller;

import com.artx.artx.product.dto.ProductRequest;
import com.artx.artx.product.dto.ProductResponse;
import com.artx.artx.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse.Create> create(@RequestPart ProductRequest.Create request, @RequestPart List<MultipartFile> files){
		return ResponseEntity.ok(productService.createProduct(request, files));
	}

	@GetMapping
	public ResponseEntity<ProductResponse.ReadAll> readAll(@RequestBody ProductRequest.Read request){
		return ResponseEntity.ok(productService.readAllProducts(request));
	}
}
