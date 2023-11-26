package com.artx.artx.product.service;

import com.artx.artx.product.model.ProductCategoryRead;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.type.ProductCategoryType;
import com.artx.artx.product.type.ProductSearchFilter;
import com.artx.artx.product.type.ProductSearchType;
import com.artx.artx.user.entity.User;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.redis.RedisCacheService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.model.ProductRead;
import com.artx.artx.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final RedisCacheService redisCacheService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value(value = "${api.products}")
	private String productsApiAddress;

	@Transactional(readOnly = true)
	public Page<ProductRead.SummaryResponse> readAllProducts(ProductCategoryType category, ProductSearchType type, String name, Pageable pageable) {

		if (type == null) {
			return productRepository.findAllByTitleWithProductImages(category, name, pageable)
					.map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product));
		}

		if (type == ProductSearchType.USER) {
			return productRepository.findAllByNicknameWithProductImages(category, name, pageable)
					.map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product));
		}

		if (type == ProductSearchType.TITLE) {
			return productRepository.findAllByTitleWithProductImages(category, name, pageable)
					.map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product));
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	@Transactional(readOnly = true)
	public ProductRead.DetailResponse readProductDetail(Long productId) {
		Product product = getProductByIdWithProductImages(productId);
		User user = product.getUser();
		redisCacheService.countProductView(productId);
		return ProductRead.DetailResponse.of(imagesApiAddress, productsApiAddress, product, user);
	}

	@Transactional(readOnly = true)
	public List<ProductRead.SummaryResponse> readMainPageProducts(ProductSearchFilter type) {

		if (type == ProductSearchFilter.LATEST) {
			return productRepository.findMainPageProductsByLatest().stream().map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		if (type == ProductSearchFilter.POPULARITY) {
			return productRepository.findMainPageProductsByPopularity().stream().map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
	}

	@Transactional(readOnly = true)
	public List<ProductCategoryRead.SummaryResponse> readCategories() {
		List<com.artx.artx.product.entity.ProductCategory> categories = productCategoryRepository.findAllWithProductCategoryImage();
		return categories.stream().map(category -> ProductCategoryRead.SummaryResponse.of(imagesApiAddress, category)).collect(Collectors.toList());
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

}
