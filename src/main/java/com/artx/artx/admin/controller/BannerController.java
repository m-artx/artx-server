package com.artx.artx.admin.controller;

import com.artx.artx.admin.model.banner.CreateBanner;
import com.artx.artx.admin.service.BannerService;
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
public class BannerController {

	private final BannerService bannerService;

	@PostMapping("/banners")
	public ResponseEntity<Void> createBanner(@RequestBody CreateBanner.Request request){
		bannerService.createBanner(request);
		return ResponseEntity.ok().build();
	}

}
