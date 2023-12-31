package com.artx.artx.domain.artist.product.api;

import com.artx.artx.domain.artist.product.application.ArtistProductImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "작가 상품 관리")
@RestController
@RequestMapping("/api/artist/products")
@RequiredArgsConstructor
@Secured("ROLE_ARTIST")
public class AritstProductImageController {

	private final ArtistProductImageService artistProductImageService;

	@PostMapping("/image")
	public ResponseEntity<String> addImage(
			@RequestParam MultipartFile file
	){
		return ResponseEntity.ok(artistProductImageService.saveProductImage(file));
	}

	@DeleteMapping("/image")
	public ResponseEntity<String> removeImage(
			@RequestParam String src
	){
		artistProductImageService.removeImage(src);
		return ResponseEntity.ok().build();
	}

}
