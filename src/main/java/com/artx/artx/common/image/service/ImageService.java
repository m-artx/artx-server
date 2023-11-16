package com.artx.artx.common.image.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.image.model.CustomMultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

	@Value("${directory.images}")
	private String imagesUploadDirectory;

	public ResponseEntity<byte[]> getImage(String filename) {
		Path path = Path.of(imagesUploadDirectory, filename);

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

	public List<MultipartFile> saveImages(List<MultipartFile> files) {
		if (files == null || files.size() <= 0) {
			throw new BusinessException(ErrorCode.NO_FILE);
		}

		List<MultipartFile> modifiedMultipartFile = new ArrayList<>();

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
				continue;
			}
			try {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
				Path path = Paths.get(imagesUploadDirectory, filename);
				Files.write(path, file.getBytes());

				MultipartFile multipartFile = CustomMultipartFile.builder()
						.filename(filename)
						.contentType(file.getContentType())
						.bytes(file.getBytes())
						.build();

				modifiedMultipartFile.add(multipartFile);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return modifiedMultipartFile;
	}

	public void deleteImages(List<String> images) {
		for (String filename : images) {
			Path path = Paths.get(imagesUploadDirectory, filename);

			File file = path.toFile();
			if (file.exists()) {
				boolean isDeleted = file.delete();
				if (!isDeleted) {
					throw new BusinessException(ErrorCode.FILE_NOT_DELETED);
				}
			}
		}
	}

	public void deleteImage(String image) {
		if(Objects.isNull(image)){
			return;
		}
		Path path = Paths.get(imagesUploadDirectory, image);

		File file = path.toFile();
		if (file.exists()) {
			boolean isDeleted = file.delete();
			if (!isDeleted) {
				throw new BusinessException(ErrorCode.FILE_NOT_DELETED);
			}
		}
	}

	public MultipartFile saveImage(MultipartFile file) {
		if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
			throw new BusinessException(ErrorCode.NO_FILE);
		}
		try {
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
			Path path = Paths.get(imagesUploadDirectory, filename);
			Files.write(path, file.getBytes());

			MultipartFile multipartFile = CustomMultipartFile.builder()
					.filename(filename)
					.contentType(file.getContentType())
					.bytes(file.getBytes())
					.build();

			return multipartFile;

		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FAILED_TO_SAVE_IMAGE);
		}
	}
}
