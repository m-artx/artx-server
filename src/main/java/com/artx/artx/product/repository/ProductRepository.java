package com.artx.artx.product.repository;

import com.artx.artx.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img "+
					"WHERE p.user.nickname = :nickname"
	)
	Page<Product> findAllByUser_Nickname(@Param("nickname") String nickname, Pageable pageable);

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img "+
					"WHERE p.title = :title"
	)
	Page<Product> findAllByTitle(@Param("title") String title, Pageable pageable);

	@Query(
			"SELECT p FROM Product p " +
					"LEFT JOIN FETCH p.productImages img "+
					"ORDER BY p.createdAt ASC LIMIT 5"
	)
	List<Product> mainPageProductsByLatest();


}
