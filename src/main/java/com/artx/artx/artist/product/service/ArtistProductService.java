package com.artx.artx.artist.product.service;

import com.artx.artx.artist.product.model.ArtistProductDelete;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.image.service.ImageService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.entity.ProductImage;
import com.artx.artx.product.entity.ProductStock;
import com.artx.artx.product.model.ProductCreate;
import com.artx.artx.product.model.ProductUpdate;
import com.artx.artx.product.repository.ProductCategoryRepository;
import com.artx.artx.product.repository.ProductImageRepository;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.product.type.ProductCategoryType;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArtistProductService {

	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final UserRepository userRepository;
	private final ImageService imageService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Value("${directory.temp-images}")
	private String tempImagesUploadDirectory;

	@Value("${directory.product-images}")
	private String productImagesUploadDirectory;

	@Value("${prefix.product-images}")
	private String productPrefix;

	@Value("${prefix.temp-images}")
	private String tempPrefix;

	private final ProductImageRepository productImageRepository;

	@Transactional
	public void deleteProduct(Long productId) {
		Product product = getProductByIdWithProductImages(productId);
		productRepository.updateToDeleted(product, true);
	}

	private Product getProductByIdWithProductImages(Long productId) {
		return productRepository.findByIdWithProductImages(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	@Transactional
	public void deleteSelectedProducts(ArtistProductDelete.Request request) {
		List<Product> products = getAllProductByIds(request.getProductIds());
		productRepository.updateToDeleted(products, true);
	}

	private List<Product> getAllProductByIds(List<Long> productIds) {
		return productRepository.findAllById(productIds);
	}

	@Transactional
	public ProductCreate.Response createProduct(UUID userId, ProductCreate.Request request) {
		List<String> productImages = request.getProductImages();
		List<String> saveProductImages = new ArrayList<>();

		for (String productImage : productImages) {
			if (productImage.equals("")) {
				continue;
			}
			String fileName = productImage.substring(productImage.lastIndexOf("/"));
			Path previousPath = Path.of(tempImagesUploadDirectory, fileName);
			Path presentPath = Path.of(productImagesUploadDirectory, fileName.replace(tempPrefix, productPrefix));

			File previousFile = previousPath.toFile();
			File presentFile = presentPath.toFile();

			boolean isRenamed = previousFile.renameTo(presentFile);

			if (isRenamed) {
				log.info("[Succeed To Save File] - {}", previousFile.getName());
				saveProductImages.add(presentFile.getName());
			} else {
				log.info("[Failed To Save File] - {}", previousFile.getName());
			}
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		ProductCategory productCategory = getProductCategoryByCategory(request.getProductCategory());
		ProductStock productStock = ProductStock.from(request);

		user.isArtist();

		Product product = productRepository.save(Product.from(request, user, productCategory, productStock));
		product.setProductImages(saveProductImages);


		return ProductCreate.Response.of(product);
	}

	@Transactional
	public ProductUpdate.Response updateProduct(Long productId, ProductUpdate.Request request) {
		Product product = productRepository.fetchProductProductStockAndProductImagesWithById(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
		List<ProductImage> productImages = product.getProductImages();

		//전부 삭제
		if (request.getProductImages() == null || request.getProductImages().size() == 0) {
			productImageRepository.deleteAllInBatch(productImages);
			imageService.removeImagesByNames(productImages.stream().map(ProductImage::getName).collect(Collectors.toList()));
		} else {
			//요청 이미지("http//~ prefix 제거")
			List<String> requestProductImages = request.getProductImages().stream().map(productImage -> productImage.substring(productImage.lastIndexOf("/") + 1).replace(tempPrefix, "").replace(productPrefix, "")).collect(Collectors.toList());

			//이전 엔티티 이미지("http 제거 ~ Prefix제거")
			List<String> previousProductImages = product.getProductImages().stream().map(ProductImage::getName).map(productImage -> productImage.replace(productPrefix, "")).collect(Collectors.toList());

			//새로운 이미지
			List<String> NotDuplicatedNewProductImages = requestProductImages.stream().filter(requestProductImage -> !previousProductImages.contains(requestProductImage)).collect(Collectors.toList());

			//지워지는 이미지
			List<String> oldProductImages = product.getProductImages().stream().filter(previousProductImage -> !requestProductImages.contains(previousProductImage.getName().replace("product_", ""))).map(ProductImage::getName).collect(Collectors.toList());

			List<String> newNames = new ArrayList<>();

			try {
				//새로운 이미지 저장
				if (NotDuplicatedNewProductImages != null && NotDuplicatedNewProductImages.size() > 0) {
					for (String fileName : NotDuplicatedNewProductImages) {
						Path previousPath = Path.of(tempImagesUploadDirectory, tempPrefix + fileName);
						Path presentPath = Path.of(productImagesUploadDirectory, productPrefix + fileName);

						File previousFile = previousPath.toFile();
						File presentFile = presentPath.toFile();

						Files.move(previousPath, presentPath, StandardCopyOption.REPLACE_EXISTING);


						boolean isRenamed = previousFile.renameTo(presentFile);

						if (isRenamed) {
							log.info("[Succeed To Save File] - {}", previousFile.getName());
						} else {
							log.info("[Failed To Save File] - {}", previousFile.getName());
						}
						newNames.add(presentFile.getName());
					}
				}
				productImageRepository.deleteAllByNameIn(oldProductImages);
				imageService.removeImagesByNames(oldProductImages);
				product.setProductImages(newNames);

			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.FAILED_TO_SAVE_IMAGE);
			}
		}
		product.update(request);

		return ProductUpdate.Response.of(product);
	}

	private com.artx.artx.product.entity.ProductCategory getProductCategoryByCategory(ProductCategoryType category) {
		return productCategoryRepository.findByType(category).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
	}


}
