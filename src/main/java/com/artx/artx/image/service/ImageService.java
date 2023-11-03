package com.artx.artx.image.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageService {

	@Value("${directory.images}")
	private String imageDirectory;

	public ResponseEntity<byte[]> saveImage(String filename) {
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
