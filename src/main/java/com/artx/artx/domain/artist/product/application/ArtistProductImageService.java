package com.artx.artx.domain.artist.product.application;

import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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

	private static final int TARGET_FILE_SIZE_KB = 20;

	public String saveProductImage(MultipartFile file) {
		try {
			if (file == null || file.isEmpty()) {
				throw new BusinessException(ErrorCode.INVALID_FILE_NAME);
			}

			String filename = "temp_" + UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
			Path tempPath = Path.of(tempImagesUploadDirectory, filename);

			BufferedImage originalImage = ImageIO.read(file.getInputStream());

			int maxWidthOrHeight = 2000;
			if (originalImage.getWidth() > maxWidthOrHeight || originalImage.getHeight() > maxWidthOrHeight) {
				originalImage = resizeImage(originalImage, maxWidthOrHeight, maxWidthOrHeight);
			}

			BufferedImage resizedImage = resizeImage(originalImage, originalImage.getWidth(), originalImage.getHeight()); // Example: Adjust to 100x100

			while (calculateFileSize(resizedImage) > TARGET_FILE_SIZE_KB * 1024) {
				resizedImage = resizeImage(resizedImage, resizedImage.getWidth()/2, resizedImage.getHeight()/2); // Example: Adjust to 100x100
			}

			ImageIO.write(resizedImage, "jpg", tempPath.toFile());

			return imagesApiAddress + filename;
		} catch (IOException ex) {
			throw new RuntimeException("이미지 저장 중 오류가 발생했습니다.", ex);
		}
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
		Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImage.createGraphics();
		g2.drawImage(resultingImage, 0, 0, null);
		g2.dispose();
		return resizedImage;
	}

	private int calculateFileSize(BufferedImage image) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(image, "jpg", baos);
			return baos.size();
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
