package com.artx.artx.domain.product.dao;

import com.artx.artx.domain.product.domain.ProductCategory;
import com.artx.artx.domain.product.domain.ProductCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	Optional<ProductCategory> findByType(ProductCategoryType type);

	@Query("SELECT pc FROM ProductCategory pc " +
			"LEFT JOIN FETCH pc.productCategoryImage pci"
	)
	List<ProductCategory> findAllWithProductCategoryImage();

}
