package com.artx.artx.product.repository;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.type.ProductCategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Product ID 작품 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages pi " +
					"LEFT JOIN FETCH p.user pu " +
					"WHERE p.id = :productId AND p.isDeleted = false"
	)
	Optional<Product> findByIdWithProductImages(Long productId);

	/**
	 * 카테고리(Nullable)와 작품명으로 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE (:category IS NULL OR p.productCategory.type = :category) " +
					"AND (:name IS NULL OR p.title = :name) " +
					"AND p.isDeleted = false"
	)
	Page<Product> findAllByTitleWithProductImages(ProductCategoryType category, String name, Pageable pageable);

	/**
	 * 카테고리(Nullable)와 유저명으로 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE (:category IS NULL OR p.productCategory.type = :category) " +
					"AND (:name IS NULL OR p.user.nickname = :name) " +
					"AND p.isDeleted = false"
	)
	Page<Product> findAllByNicknameWithProductImages(ProductCategoryType category, String name, Pageable pageable);

	/**
	 * 메인페이지 작품 최신순 10개 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE p.isDeleted = false " +
					"ORDER BY p.createdAt DESC LIMIT 10"

	)
	List<Product> findMainPageProductsByLatest();

	/**
	 * 메인페이지 작품 인기순 10개 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE p.isDeleted = false " +
					"ORDER BY p.views DESC LIMIT 10"
	)
	List<Product> findMainPageProductsByPopularity();

	@Modifying
	@Query("UPDATE Product p SET p.isDeleted = :isDeleted WHERE p = :product")
	void updateToDeleted(Product product, boolean isDeleted);

	@Modifying
	@Query("UPDATE Product p SET p.isDeleted = :isDeleted WHERE p IN :products")
	void updateToDeleted(List<Product> products, boolean isDeleted);

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"LEFT JOIN FETCH p.productStock ps " +
					"LEFT JOIN FETCH p.productCategory pc " +
					"WHERE p.id = :productId"
	)
	Optional<Product> fetchProductProductStockAndProductImagesWithById(Long productId);

}
