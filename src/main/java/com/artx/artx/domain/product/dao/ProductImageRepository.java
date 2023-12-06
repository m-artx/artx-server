package com.artx.artx.domain.product.dao;

import com.artx.artx.domain.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

	void deleteAllByNameIn(List<String>images);

}
