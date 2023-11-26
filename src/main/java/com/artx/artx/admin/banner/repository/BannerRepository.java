package com.artx.artx.admin.banner.repository;


import com.artx.artx.admin.banner.entity.Banner;
import com.artx.artx.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

	boolean existsByProduct(Product product);
}
