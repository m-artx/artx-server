package com.artx.artx.admin.repository;


import com.artx.artx.admin.entity.Banner;
import com.artx.artx.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("SELECT b FROM Banner b LEFT JOIN FETCH b.product p ORDER BY b.createdAt ASC")
	List<Banner> findAllWithProduct();

	boolean existsByProduct(Product product);
}
