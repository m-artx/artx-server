package com.artx.artx.artist.product.service;

import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistProductImageService {

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value("${directory.temp-images}")
	private String tempImagesUploadDirectory;

	@Value("${directory.product-images}")
	private String imagesUploadDirectory;

	public String saveProductImage(MultipartFile file) {
		if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
			throw new BusinessException(ErrorCode.INVALID_FILE_NAME);
		}
		try {
			String filename = "temp_" + UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
			Path path = Paths.get(tempImagesUploadDirectory, filename);
			Files.write(path, file.getBytes());
			return imagesApiAddress + filename;

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.FILE_NOT_SAVED);
		}

	}

	public void removeImage(String src) {
		String filename = src.substring(src.lastIndexOf("/"));
		Path path;

		if (isTempImage(filename)) {
			path = Path.of(tempImagesUploadDirectory, filename);

		} else {
			path = Path.of(imagesUploadDirectory, filename);

		}

		try {
			File file = path.toFile();
			file.delete();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException(ErrorCode.FILE_NOT_DELETED);
		}
	}

	private static boolean isTempImage(String filename) {
		return filename.contains("temp");
	}

}
