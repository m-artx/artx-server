package com.artx.artx.common.image.service;

import com.artx.artx.common.image.model.CustomMultipartFile;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
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

	@Value("${directory.product-images}")
	private String productImagesUploadDirectory;

	@Value("${directory.temp-images}")
	private String tempImagesUploadDirectory;

	@Value("${directory.thumbnail-images}")
	private String thumbnailImagesUploadDirectory;

	@Value("${directory.profile-images}")
	private String profileImagesUploadDirectory;

	@Value("${directory.permission-images}")
	private String permissionImagesUploadDirectory;

	@Value("${prefix.permission-images}")
	private String permissionImagesPrefix;

	@Value("${prefix.product-images}")
	private String productImagesPrefix;

	@Value("${prefix.temp-images}")
	private String tempImagesPrefix;

	@Value("${prefix.thumbnail-images}")
	private String thumbnailImagesPrefix;

	@Value("${prefix.profile-images}")
	private String profileImagesPrefix;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	public ResponseEntity<byte[]> getImage(String filename) {
		Path path = getPathByFilename(filename);

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

	private Path getPathByFilename(String filename) {
		Path path;
		//PREFIX별 다른 Path로 지정
		if (filename.contains(productImagesPrefix)) {
			path = Path.of(productImagesUploadDirectory, filename);
		} else if (filename.contains(tempImagesPrefix)) {
			path = Path.of(tempImagesUploadDirectory, filename);
		} else if (filename.contains(thumbnailImagesPrefix)){
			path = Path.of(thumbnailImagesUploadDirectory, filename);
		} else if (filename.contains(profileImagesPrefix)) {
			path = Path.of(profileImagesUploadDirectory, filename);
		} else if (filename.contains(permissionImagesPrefix)) {
			path = Path.of(permissionImagesUploadDirectory, filename);
		} else {
			throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
		}
		return path;
	}

	public List<MultipartFile> savePermissionImages(List<MultipartFile> files) {
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
				Path path = Paths.get(permissionImagesUploadDirectory, permissionImagesPrefix + filename);
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

	public void removeImagesByNames(List<String> images) {
		for (String filename : images) {
			Path path = getPathByFilename(filename);

			File file = path.toFile();
			if (file.exists()) {
				boolean isDeleted = file.delete();
				if (!isDeleted) {
					throw new BusinessException(ErrorCode.FILE_NOT_DELETED);
				}
			}
		}
	}

	public String saveProfileImage(MultipartFile file) {
		if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
			throw new BusinessException(ErrorCode.NO_FILE);
		}
		try {
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
			Path path = Paths.get(profileImagesUploadDirectory, profileImagesPrefix + filename);
			Files.write(path, file.getBytes());

			return profileImagesPrefix + filename;

		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FAILED_TO_SAVE_IMAGE);
		}
	}

	public String savePermissionImage(MultipartFile file) {
		if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
			throw new BusinessException(ErrorCode.NO_FILE);
		}
		try {
			String filename = permissionImagesPrefix + UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
			Path path = getPathByFilename(filename);
			Files.write(path, file.getBytes());

			return filename;

		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FAILED_TO_SAVE_IMAGE);
		}
	}

	public void removeImage(String filename) {
		if (Objects.isNull(filename)) {
			return;
		}
		Path path = getPathByFilename(filename);


		File file = path.toFile();
		if (file.exists()) {
			boolean isDeleted = file.delete();
			if (!isDeleted) {
				throw new BusinessException(ErrorCode.FILE_NOT_DELETED);
			}
		}
	}
}
