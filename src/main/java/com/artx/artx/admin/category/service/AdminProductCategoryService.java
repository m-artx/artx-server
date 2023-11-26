package com.artx.artx.admin.category.service;

import com.artx.artx.admin.category.model.ProductCategoryCreate;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.entity.ProductCategoryImage;
import com.artx.artx.product.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminProductCategoryService {

	private final ProductCategoryRepository productCategoryRepository;

	@Value(value = "${directory.product-images}")
	private String imagesUploadDirectory;

	@Transactional
	public void createCategory(MultipartFile file, ProductCategoryCreate.Request request) {
		try {
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
			Path path = Paths.get(imagesUploadDirectory, filename);
			Files.write(path, file.getBytes());

			ProductCategoryImage productCategoryImage = ProductCategoryImage.builder()
					.representativeImage(filename)
					.build();
			productCategoryRepository.save(ProductCategory
					.builder()
					.type(request.getProductCategory())
					.description(request.getProductCategoryDescription())
					.productCategoryImage(productCategoryImage)
					.build()
			);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_CATEGORY);
		}
	}
}
