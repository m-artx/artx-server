package com.artx.artx.product.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.product.dto.ProductRequest;
import com.artx.artx.product.dto.ProductResponse;
import com.artx.artx.product.model.Product;
import com.artx.artx.product.model.ProductCategory;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.model.ProductImage;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.user.model.User;
import com.artx.artx.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final UserService userService;

	@Value(value = "${file.upload-dir}")
	private String uploadDir;

	@Transactional
	public ProductResponse.Create createProduct(ProductRequest.Create request, List<MultipartFile> files) {
		User user = getUserById(request.getUserId());
		user.canCreateProduct();
		Product product = productRepository.save(
				Product.from(request, getProductCategoryById(request.getProductCategoryId()), user)
		);
		product.saveProductImages(saveProductImages(files));
		return ProductResponse.Create.from(product);
	}

	private List<ProductImage> saveProductImages(List<MultipartFile> files) {
		List<ProductImage> productImages = new ArrayList<>();
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			try {
				String filename = file.getOriginalFilename();
				String dir = uploadDir;
				Path path = Paths.get(dir, filename);
				Files.write(path, file.getBytes());

				productImages.add(ProductImage.builder()
						.name(filename)
						.type(file.getContentType())
						.src(path.toAbsolutePath().toString())
						.build()
				);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return productImages;
	}

	private ProductCategory getProductCategoryById(Long productCategoryId) {
		return productCategoryRepository.findById(productCategoryId)
				.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
	}

	/**
	 * TODO: PAGINATION 추가 필요
	 */
	@Transactional
	public ProductResponse.ReadAll readAllProducts(ProductRequest.Read request) {
		return ProductResponse.ReadAll.builder()
				.productList(productRepository.findAllByUser_UserId(request.getUserId()))
				.build();
	}

	/**
	 * TODO: 동시성 이슈 체크
	 */
	@Transactional
	public void increaseQuantity() {

	}

	@Transactional
	public void decreaseQuantity() {

	}

	private User getUserById(UUID userId) {
		return userService.getUserByUserId(userId);
	}
}
