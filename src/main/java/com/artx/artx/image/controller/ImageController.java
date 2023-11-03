package com.artx.artx.image.controller;

import com.artx.artx.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	@GetMapping("/{filename}")
	public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
		return imageService.saveImage(filename);
	}

}
