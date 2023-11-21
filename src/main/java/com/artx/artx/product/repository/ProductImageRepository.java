package com.artx.artx.product.repository;

import com.artx.artx.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	void deleteAllByNameIn(List<String>images);

}
