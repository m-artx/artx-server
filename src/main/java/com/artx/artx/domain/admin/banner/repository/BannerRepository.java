package com.artx.artx.domain.admin.banner.repository;


import com.artx.artx.domain.admin.banner.entity.Banner;
import com.artx.artx.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

	boolean existsByProduct(Product product);
}
