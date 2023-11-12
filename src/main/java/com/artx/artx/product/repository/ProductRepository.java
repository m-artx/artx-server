package com.artx.artx.product.repository;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
					"WHERE p.id = :productId"
	)
	Optional<Product> findByIdWithProductImages(@Param("productId") Long productId);

	/**
	 * 카테고리(Nullable)와 작품명으로 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE (:category IS NULL OR p.productCategory.type = :category) " +
					"AND (:name IS NULL OR p.title = :name) "
	)
	Page<Product> findAllByTitleWithProductImages(@Param("category") Category category, @Param("name") String name, Pageable pageable);

	/**
	 * 카테고리(Nullable)와 유저명으로 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE (:category IS NULL OR p.productCategory.type = :category) " +
					"AND (:name IS NULL OR p.user.nickname = :name) "
	)
	Page<Product> findAllByNicknameWithProductImages(@Param("category") Category category, @Param("name") String name, Pageable pageable);

	/**
	 * 메인페이지 작품 최신순 10개 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"ORDER BY p.createdAt ASC LIMIT 10"
	)
	List<Product> findMainPageProductsByLatest();

	/**
	 * 메인페이지 작품 인기순 10개 조회
	 * @return Product + ProductImages (N+1)
	 */
	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"ORDER BY p.views DESC LIMIT 10"
	)
	List<Product> findMainPageProductsByPopularity();

}
