package com.artx.artx.admin.controller;

import com.artx.artx.admin.model.CreateBanner;
import com.artx.artx.admin.model.CreateProductCategory;
import com.artx.artx.admin.model.ReadBanner;
import com.artx.artx.admin.service.BannerService;
import com.artx.artx.admin.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final BannerService bannerService;
	private final ProductCategoryService productCategoryService;
//
//	@GetMapping("/test")
//	public ResponseEntity<String> test(){
//		return ResponseEntity.ok("테스트 성공");
//	}

	@PostMapping("/banners")
	public ResponseEntity<Void> createBanner(@RequestBody CreateBanner.Request request){
		bannerService.createBanner(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/categories")
	public ResponseEntity<Void> createCategory(@RequestPart MultipartFile file, @RequestPart CreateProductCategory.Request request){
		productCategoryService.createCategory(file, request);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<ReadBanner.Response>> readBanners(){
		return ResponseEntity.ok(bannerService.allBanners());
	}

}
