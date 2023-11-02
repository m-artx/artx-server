package com.artx.artx.banner.controller;

import com.artx.artx.banner.model.BannerRequest;
import com.artx.artx.banner.model.BannerResponse;
import com.artx.artx.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {

	private final BannerService bannerService;

	//TODO: 관리자 권한
	@PostMapping
	public ResponseEntity<Void> addBanner(@RequestBody BannerRequest.Create request){
		bannerService.addBanner(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<BannerResponse>> readBanners(){
		return ResponseEntity.ok(bannerService.allBanners());
	}

}
