package com.artx.artx.domain.product.dao;

import com.artx.artx.domain.product.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

	Optional<ProductStock> findByProduct_Id(Long productId);
}
