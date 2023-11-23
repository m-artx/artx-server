package com.artx.artx.admin.category.controller;

import com.artx.artx.admin.category.model.ProductCategoryCreate;
import com.artx.artx.admin.banner.service.AdminBannerService;
import com.artx.artx.admin.notice.service.AdminNoticeService;
import com.artx.artx.admin.permission.service.AdminPermissionService;
import com.artx.artx.admin.category.service.AdminProductCategoryService;
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
public class AdminCategoryController {

	private final AdminBannerService bannerService;
	private final AdminNoticeService noticeService;
	private final AdminPermissionService permissionService;
	private final AdminProductCategoryService productCategoryService;


	@PostMapping
	public ResponseEntity<Void> createCategory(@RequestPart MultipartFile file, @RequestPart ProductCategoryCreate.Request request){
		productCategoryService.createCategory(file, request);
		return ResponseEntity.ok().build();
	}

}

