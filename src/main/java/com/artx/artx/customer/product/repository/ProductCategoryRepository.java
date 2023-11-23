package com.artx.artx.customer.product.repository;

import com.artx.artx.customer.product.entity.ProductCategory;
import com.artx.artx.customer.product.type.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	Optional<ProductCategory> findByType(Category type);

	@Query("SELECT pc FROM ProductCategory pc " +
			"LEFT JOIN FETCH pc.productCategoryImage pci"
	)
	List<ProductCategory> findAllWithProductCategoryImage();

}
