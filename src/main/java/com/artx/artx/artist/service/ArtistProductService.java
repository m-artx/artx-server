package com.artx.artx.artist.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.artist.model.ArtistProductDelete;
import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.entity.ProductStock;
import com.artx.artx.product.model.ProductCreate;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.product.type.Category;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArtistProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final UserRepository userRepository;
	private final ImageService imageService;

	@Transactional
	public void deleteProduct(Long productId) {
		Product product = getProductByIdWithProductImages(productId);
		productRepository.updateToDeleted(product, true);
	}

	private Product getProductByIdWithProductImages(Long productId) {
		return productRepository.findByIdWithProductImages(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	@Transactional
	public void deleteAllProducts(ArtistProductDelete.Request request) {
		List<Product> products = getAllProductByIds(request.getProductIds());
		productRepository.updateToDeleted(products, true);
	}

	private List<Product> getAllProductByIds(List<Long> productIds) {
		return productRepository.findAllById(productIds);
	}

	@Transactional
	public ProductCreate.Response createProduct(ProductCreate.Request request, List<MultipartFile> files) {
		User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		ProductCategory productCategory = getProductCategoryByCategory(request.getProductCategory());
		ProductStock productStock = ProductStock.from(request);

		user.isArtist();

		Product product = productRepository.save(
				Product.from(
						request,
						user,
						productCategory,
						productStock
				)
		);

		if (existsFiles(files)) {
			List<MultipartFile> modifiedProductImages = imageService.saveImages(files);
			product.setProductImages(modifiedProductImages);
		}

		return ProductCreate.Response.of(product);
	}

	private boolean existsFiles(List<MultipartFile> files) {
		return files != null && files.size() > 0;
	}

	private ProductCategory getProductCategoryByCategory(Category category) {
		return productCategoryRepository.findByType(category)
				.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
	}

}
