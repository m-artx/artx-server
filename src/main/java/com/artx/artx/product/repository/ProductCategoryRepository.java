package com.artx.artx.product.repository;

import com.artx.artx.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	Optional<ProductCategory> findByName(String name);

	@Query("SELECT pc FROM ProductCategory pc LEFT JOIN FETCH pc.productCategoryImage pci")
	List<ProductCategory> findAllWithProductCategoryImage();
}
