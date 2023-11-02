package com.artx.artx.product.repository;

import com.artx.artx.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE p.user.nickname = :nickname"
	)
	Page<Product> findAllByUser_Nickname(@Param("nickname") String nickname, Pageable pageable);

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE p.title = :title"
	)
	Page<Product> findAllByTitle(@Param("title") String title, Pageable pageable);

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"ORDER BY p.createdAt ASC LIMIT 10"
	)
	List<Product> findMainPageProductsByLatest();

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img " +
					"WHERE p.productCategory.id = :categoryId " +
					"ORDER BY p.createdAt ASC"
	)
	Page<Product> findProductsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);


	@Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
	Optional<Product> findByIdWithProductImages(@Param("productId") Long productId);

}
