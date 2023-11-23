package com.artx.artx.customer.product.repository;

import com.artx.artx.customer.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	void deleteAllByNameIn(List<String>images);

}
