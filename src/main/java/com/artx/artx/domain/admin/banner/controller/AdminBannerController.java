package com.artx.artx.domain.admin.banner.controller;

import com.artx.artx.domain.admin.banner.model.BannerCreate;
import com.artx.artx.domain.admin.banner.service.AdminBannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminBannerController {

	private final AdminBannerService bannerService;

	@PostMapping("/banners")
	public ResponseEntity<Void> createBanner(@RequestBody BannerCreate.Request request){
		bannerService.createBanner(request);
		return ResponseEntity.ok().build();
	}

}
