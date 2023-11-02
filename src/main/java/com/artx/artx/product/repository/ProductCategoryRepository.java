package com.artx.artx.product.repository;

import com.artx.artx.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	Optional<ProductCategory> findByName(String name);
}
