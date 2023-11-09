package com.artx.artx.product.repository;

import com.artx.artx.product.entity.ProductCategory;
import com.artx.artx.product.type.ProductCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	Optional<ProductCategory> findByType(ProductCategoryType type);

	@Query("SELECT pc FROM ProductCategory pc LEFT JOIN FETCH pc.productCategoryImage pci")
	List<ProductCategory> findAllWithProductCategoryImage();

}
