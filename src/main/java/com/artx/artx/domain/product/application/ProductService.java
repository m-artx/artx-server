package com.artx.artx.domain.product.application;

import com.artx.artx.domain.product.domain.Product;
import com.artx.artx.domain.product.domain.ProductCategory;
import com.artx.artx.domain.product.dto.ProductCategoryRead;
import com.artx.artx.domain.product.dto.ProductRead;
import com.artx.artx.domain.product.dao.ProductCategoryRepository;
import com.artx.artx.domain.product.dao.ProductRepository;
import com.artx.artx.domain.product.domain.ProductCategoryType;
import com.artx.artx.domain.product.type.ProductSearchFilter;
import com.artx.artx.domain.product.type.ProductSearchType;
import com.artx.artx.domain.user.domain.User;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value(value = "${api.products}")
	private String productsApiAddress;

	private final RedisTemplate<String, Long> redisTemplate;

	public void countProductView(Long productId){
		HashOperations<String, Long, Long> ops = redisTemplate.opsForHash();
		ops.increment("productCountView", productId, 1);
	}

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
		countProductView(productId);
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
		List<ProductCategory> categories = productCategoryRepository.findAllWithProductCategoryImage();
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
