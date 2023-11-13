package com.artx.artx.product.controller;

import com.artx.artx.product.model.CreateProduct;
import com.artx.artx.product.model.DeleteProduct;
import com.artx.artx.product.model.ReadProduct;
import com.artx.artx.product.model.ReadProductCategory;
import com.artx.artx.product.service.ProductService;
import com.artx.artx.product.type.Filter;
import com.artx.artx.product.type.Category;
import com.artx.artx.product.type.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "작품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "작품 등록", description = "작가는 새로운 작품을 등록할 수 있다.")
	@PostMapping
	public ResponseEntity<CreateProduct.Response> create(
			@RequestPart CreateProduct.Request request,
			@Nullable @RequestPart List<MultipartFile> files
	){
		return ResponseEntity.ok(productService.createProduct(request, files));
	}

	@Operation(summary = "작품 조회", description = "카테고리와 작가명 또는 제목으로 작품을 전체 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<Page<ReadProduct.SimpleResponse>> searchProducts(
			@Nullable @RequestParam Category category,
			@Nullable @RequestParam Type type,
			@Nullable @RequestParam String name,
			Pageable pageable){
		return ResponseEntity.ok(productService.readAllProducts(category, type, name , pageable));
	}

	@Operation(summary = "작품 삭제", description = "작품을 삭제할 수 있다.")
	@DeleteMapping("/{productId}")
	public ResponseEntity<Page<ReadProduct.SimpleResponse>> deleteProduct(@PathVariable Long productId){
		productService.deleteProduct(productId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "작품 선택 삭제", description = "작품을 선택 삭제할 수 있다.")
	@DeleteMapping
	public ResponseEntity<Page<ReadProduct.SimpleResponse>> deleteAllProducts(@RequestBody DeleteProduct.Request request){
		productService.deleteAllProducts(request);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "작품 상세 페이지 조회", description = "특정 작품의 상세 페이지를 조회할 수 있다.")
	@GetMapping("/{productId}")
	public ResponseEntity<ReadProduct.Response> readProductDetail(@PathVariable Long productId){
		return ResponseEntity.ok(productService.readProductDetail(productId));
	}

	@Operation(summary = "메인 페이지 조회", description = "등록순 및 인기순으로 작품 10개를 조회할 수 있다.")
	@GetMapping("/main")
	public ResponseEntity<List<ReadProduct.SimpleResponse>> mainPageProducts(@RequestParam Filter type){
		return ResponseEntity.ok(productService.readMainPageProducts(type));
	}

	@Operation(summary = "전체 카테고리 조회", description = "모든 카테고리의 이름, 상세 설명, 대표 이미지를 조회할 수 있다.")
	@GetMapping("/categories")
	public ResponseEntity<List<ReadProductCategory.ResponseAll>> readCategories(){
		return ResponseEntity.ok(productService.readCategories());
	}

}
