package com.artx.artx.product.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.service.RedisCacheService;
import com.artx.artx.image.service.ImageService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.entity.ProductStock;
import com.artx.artx.product.model.*;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.product.type.Category;
import com.artx.artx.product.type.Filter;
import com.artx.artx.product.type.Type;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final UserService userService;
	private final RedisCacheService redisCacheService;
	private final ImageService imageService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value(value = "${api.products}")
	private String productsApiAddress;

	@Transactional
	public CreateProduct.Response createProduct(CreateProduct.Request request, List<MultipartFile> files) {
		User user = userService.getUserByUserId(request.getUserId());
		user.canCreateProduct();

		Product product = productRepository.save(
				Product.from(request)
		);

		product.setCategory(getProductCategoryById(request.getProductCategory()));
		product.setUser(user);
		product.setProductStock(ProductStock.from(request));

		if(existsFiles(files)){
			List<MultipartFile> modifiedProductImages = imageService.saveProductImages(files);
			product.setProductImages(modifiedProductImages);
		}

		return CreateProduct.Response.from(product);
	}

	private boolean existsFiles(List<MultipartFile> files) {
		return files != null && files.size() > 0;
	}

	@Transactional(readOnly = true)
	public Page<ReadProduct.SimpleResponse> readAllProducts(Category category, Type type, String name, Pageable pageable) {

		if (type == null){
			return productRepository.findAllByTitleWithProductImages(category, name, pageable)
					.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
		}

		if (type == Type.USER) {
			return productRepository.findAllByNicknameWithProductImages(category, name, pageable)
					.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
		}

		if (type == Type.TITLE) {
			return productRepository.findAllByTitleWithProductImages(category, name, pageable)
					.map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product));
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	@Transactional(readOnly = true)
	public ReadProduct.Response readProductDetail(Long productId) {
		Product product = getProductByIdWithProductImages(productId);
		redisCacheService.countProductView(productId);
		return ReadProduct.Response.from(imagesApiAddress, product);
	}

	@Transactional(readOnly = true)
	public List<ReadProduct.SimpleResponse> readMainPageProducts(Filter type) {

		if (type == Filter.LATEST) {
			return productRepository.findMainPageProductsByLatest().stream().map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		if (type == Filter.POPULARITY) {
			return productRepository.findMainPageProductsByPopularity().stream().map(product -> ReadProduct.SimpleResponse.from(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	@Transactional(readOnly = true)
	public List<ReadProductCategory.ResponseAll> readCategories() {
		List<ProductCategory> categories = productCategoryRepository.findAllWithProductCategoryImage();
		return categories.stream().map(category -> ReadProductCategory.ResponseAll.from(imagesApiAddress, category)).collect(Collectors.toList());
	}

	@Transactional
	public void deleteProduct(Long productId) {
		Product product = getProductByIdWithProductImages(productId);
		productRepository.updateToDeleted(product, true);
	}

	@Transactional
	public void deleteAllProducts(DeleteProduct.Request request) {
		List<Product> products = getAllProductByIds(request.getProductIds());
		productRepository.updateToDeleted(products, true);
	}

	public Product getProductById(Long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	private Product getProductByIdWithProductImages(Long productId) {
		return productRepository.findByIdWithProductImages(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	public List<Product> getAllProductByIds(List<Long> productId) {
		return productRepository.findAllById(productId);
	}

	private ProductCategory getProductCategoryById(Category category) {
		return productCategoryRepository.findByType(category)
				.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
	}

	public void requestCommission(CreateCommission.Request request, Long productId) {
		User user = userService.getUserByUserId(request.getUserId());
		Product product = getProductById(productId);


	}
}
