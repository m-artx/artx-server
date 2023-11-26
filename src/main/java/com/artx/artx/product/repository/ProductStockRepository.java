package com.artx.artx.product.repository;

import com.artx.artx.product.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

	Optional<ProductStock> findByProduct_Id(Long productId);
}