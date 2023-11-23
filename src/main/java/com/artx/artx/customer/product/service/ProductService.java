package com.artx.artx.customer.product.service;

import com.artx.artx.customer.product.entity.ProductCategory;
import com.artx.artx.customer.product.model.ProductCategoryRead;
import com.artx.artx.customer.product.repository.ProductCategoryRepository;
import com.artx.artx.customer.product.type.Category;
import com.artx.artx.customer.product.type.Filter;
import com.artx.artx.customer.product.type.Type;
import com.artx.artx.common.user.entity.User;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.etc.redis.RedisCacheService;
import com.artx.artx.customer.product.entity.Product;
import com.artx.artx.customer.product.model.ProductRead;
import com.artx.artx.customer.product.repository.ProductRepository;
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
	public Page<ProductRead.SummaryResponse> readAllProducts(Category category, Type type, String name, Pageable pageable) {

		if (type == null) {
			return productRepository.findAllByTitleWithProductImages(category, name, pageable)
					.map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product));
		}

		if (type == Type.USER) {
			return productRepository.findAllByNicknameWithProductImages(category, name, pageable)
					.map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product));
		}

		if (type == Type.TITLE) {
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
	public List<ProductRead.SummaryResponse> readMainPageProducts(Filter type) {

		if (type == Filter.LATEST) {
			return productRepository.findMainPageProductsByLatest().stream().map(product -> ProductRead.SummaryResponse.of(imagesApiAddress, productsApiAddress, product)).collect(Collectors.toList());
		}

		if (type == Filter.POPULARITY) {
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
