package com.artx.artx.product.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.product.dto.ProductRequest;
import com.artx.artx.product.dto.ProductResponse;
import com.artx.artx.product.model.Product;
import com.artx.artx.product.model.ProductCategory;
import com.artx.artx.product.model.ProductImage;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.product.type.FilterType;
import com.artx.artx.product.type.SearchType;
import com.artx.artx.user.model.User;
import com.artx.artx.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final UserService userService;

	@Value(value = "${directory.images}")
	private String uploadDir;

	@Value(value = "${server.full-url}")
	private String serverUrl;

	@Transactional
	public ProductResponse.Create createProduct(ProductRequest.Create request, List<MultipartFile> files) {
		User user = getUserById(request.getUserId());
		user.canCreateProduct();
		Product product = productRepository.save(
				Product.from(request, getProductCategoryById(request.getProductCategoryId()), user)
		);
		product.saveProductImages(saveProductImages(product, uploadDir, files));
		return ProductResponse.Create.from(product);
	}

	private List<ProductImage> saveProductImages(Product product, String fileDirectory, List<MultipartFile> files) {
		List<ProductImage> productImages = new ArrayList<>();

		for(int i = 0; i < files.size(); i++){
			MultipartFile file = files.get(i);

			try {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
				Path path = Paths.get(fileDirectory, filename);
				Files.write(path, file.getBytes());
				productImages.add(ProductImage.builder()
						.name(filename)
						.type(file.getContentType())
						.build()
				);

				if(i == 0){
					product.setReresentativeImage(filename);
				}

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

	@Transactional
	public Page<ProductResponse.ReadAll> searchProducts(SearchType type, String name, Pageable pageable) {
		if(type == SearchType.USER){
			return productRepository.findAllByUser_Nickname(name, pageable).map(product -> ProductResponse.ReadAll.from(serverUrl, product));
		}

		if(type == SearchType.TITLE){
			return productRepository.findAllByTitle(name, pageable).map(product -> ProductResponse.ReadAll.from(serverUrl, product));
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	@Transactional
	public List<ProductResponse.ReadAll> mainPageProducts(FilterType type) {
		if(type == FilterType.LATEST){
			return productRepository.mainPageProductsByLatest().stream().map(product -> ProductResponse.ReadAll.from(serverUrl, product)).collect(Collectors.toList());
		}
//
//		if(type == FilterType.POPULARITY){
//			return productRepository.findAllByTitle(name, pageable).map(product -> ProductResponse.ReadAll.from(serverUrl, product));
//		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
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
