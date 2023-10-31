package com.artx.artx.product.repository;

import com.artx.artx.product.dto.ProductDto;
import com.artx.artx.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(
			"SELECT new com.artx.artx.product.dto.ProductDto(p.id, p.title, p.quantity, p.price) FROM Product p " +
					"WHERE p.user.userId = :userId"
	)
	List<ProductDto> findAllByUser_UserId(@Param("userId") UUID userId);

}
