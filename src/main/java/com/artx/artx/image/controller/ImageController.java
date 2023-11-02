package com.artx.artx.image.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Value("${directory.images}")
	private String imageDirectory;

	@GetMapping("/{filename}")
	public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
		Path path = Path.of(imageDirectory, filename);

		try {
			File file = path.toFile();
			FileInputStream inputStream = new FileInputStream(file);
			byte[] imageByteArr = inputStream.readAllBytes();
			return ResponseEntity.ok().contentType(MediaType.valueOf(Files.probeContentType(path))).body(imageByteArr);

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok().build();
		}
	}
}
