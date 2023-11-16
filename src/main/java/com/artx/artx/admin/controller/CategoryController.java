package com.artx.artx.admin.controller;

import com.artx.artx.admin.model.category.CreateProductCategory;
import com.artx.artx.admin.service.BannerService;
import com.artx.artx.admin.service.NoticeService;
import com.artx.artx.admin.service.PermissionService;
import com.artx.artx.admin.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class CategoryController {

	private final BannerService bannerService;
	private final NoticeService noticeService;
	private final PermissionService permissionService;
	private final ProductCategoryService productCategoryService;


	@PostMapping
	public ResponseEntity<Void> createCategory(@RequestPart MultipartFile file, @RequestPart CreateProductCategory.Request request){
		productCategoryService.createCategory(file, request);
		return ResponseEntity.ok().build();
	}

}

