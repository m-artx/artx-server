package com.artx.artx.banner.controller;

import com.artx.artx.banner.model.BannerRequest;
import com.artx.artx.banner.model.BannerResponse;
import com.artx.artx.banner.model.ProductCategoryRequest;
import com.artx.artx.banner.service.BannerService;
import com.artx.artx.banner.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final BannerService bannerService;
	private final ProductCategoryService productCategoryService;

	@PostMapping("/banners")
	public ResponseEntity<Void> createBanner(@RequestBody BannerRequest.Create request){
		bannerService.createBanner(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/categories")
	public ResponseEntity<Void> createCategory(@RequestPart MultipartFile file, @RequestPart ProductCategoryRequest.Create request){
		productCategoryService.createCategory(file, request);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<BannerResponse>> readBanners(){
		return ResponseEntity.ok(bannerService.allBanners());
	}

}
