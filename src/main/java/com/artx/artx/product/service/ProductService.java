package com.artx.artx.product.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.service.RedisCacheService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.entity.ProductImage;
import com.artx.artx.product.entity.ProductStock;
import com.artx.artx.product.model.CreateProduct;
import com.artx.artx.product.model.ReadProduct;
import com.artx.artx.product.model.ReadProductCategory;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.product.type.FilterType;
import com.artx.artx.product.type.ProductCategoryType;
import com.artx.artx.product.type.SearchType;
import com.artx.artx.user.entity.User;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final UserService userService;
	private final RedisCacheService redisCacheService;

	@Value(value = "${directory.images}")
	private String imagesUploadDirectory;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value(value = "${api.products}")
	private String productsApiAddress;

	@Transactional
	public CreateProduct.Response createProduct(CreateProduct.Request request, List<MultipartFile> files) {
		User user = getUserById(request.getUserId());
		user.canCreateProduct();

		// 작품 등록
		Product product = productRepository.save(
				Product.from(request)
		);

		product.setCategory(getProductCategoryById(request.getProductCategoryType()));
		product.setUser(user);
		product.setProductStock(ProductStock.builder().quantity(request.getProductQuantity()).build());
		product.saveProductImages(saveProductImages(product, imagesUploadDirectory, files));

		return CreateProduct.Response.from(product);
	}

	@Transactional
	public List<ProductImage> saveProductImages(Product product, String uploadDir, List<MultipartFile> files) {
		List<ProductImage> productImages = new ArrayList<>();

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);

			try {
				String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
				Path path = Paths.get(uploadDir, filename);
				Files.write(path, file.getBytes());
				productImages.add(ProductImage.builder()
						.name(filename)
						.type(file.getContentType())
						.build()
				);

				if (i == 0) {
					product.setReresentativeImage(filename);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return productImages;
	}

	private ProductCategory getProductCategoryById(ProductCategoryType productCategoryType) {
		return productCategoryRepository.findByType(productCategoryType)
				.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
	}


	// 작품 유저 또는 제목 검색
	@Transactional
	public Page<ReadProduct.SimpleResponse> searchProducts(SearchType type, String name, Pageable pageable) {
		if (type == SearchType.USER) {
			return productRepository.findAllByUser_Nickname(name, pageable)
					.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
		}

		if (type == SearchType.TITLE) {
			return productRepository.findAllByTitle(name, pageable)
					.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	// 특정 작품 상세페이지
	@Transactional(readOnly = true)
	public ReadProduct.Response readProductDetail(Long productId) {
		Product product = productRepository.findByIdWithProductImages(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

		redisCacheService.countProductView(productId);
		return ReadProduct.Response.from(imagesApiAddress, product);
	}

	// 최근 작품 및 인기 작품
	@Transactional(readOnly = true)
	public List<ReadProduct.SimpleResponse> readMainPageProducts(FilterType type) {
		if (type == FilterType.LATEST) {
			return productRepository.findMainPageProductsByLatest().stream().map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		if (type == FilterType.POPULARITY) {
			return productRepository.findMainPageProductsByPopularity().stream().map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	@Transactional(readOnly = true)
	public List<ReadProductCategory.ResponseAll> readCategories() {
		List<ProductCategory> categories = productCategoryRepository.findAllWithProductCategoryImage();
		return categories.stream().map(category -> ReadProductCategory.ResponseAll.from(imagesApiAddress, category)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<ReadProduct.SimpleResponse> readProductsByCategory(ProductCategoryType type, Pageable pageable) {

		if (type == ProductCategoryType.ALL) {
			return productRepository.findProductsByCategory(pageable)
					.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
		}

		return productRepository.findProductsByCategory(type, pageable)
				.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
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

	public Product getProductById(Long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	public List<Product> getAllProductByIds(Set<Long> productId) {
		return productRepository.findAllById(productId);
	}

}
