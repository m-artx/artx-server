package com.artx.artx.admin.banner.service;

import com.artx.artx.admin.banner.entity.Banner;
import com.artx.artx.admin.banner.model.BannerCreate;
import com.artx.artx.admin.banner.model.BannerRead;
import com.artx.artx.admin.banner.repository.BannerRepository;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.customer.product.entity.Product;
import com.artx.artx.customer.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminBannerService {

	private final ProductService productService;
	private final BannerRepository bannerRepository;

	@Value(value = "${api.products}")
	private String productsApiAddress;

	@Transactional
	public void createBanner(BannerCreate.Request request) {
		Product product = productService.getProductById(request.getProductId());
		if (bannerRepository.existsByProduct(product)) {
			throw new BusinessException(ErrorCode.DUPLICATED_BANNER);
		}
		bannerRepository.save(Banner.from(productsApiAddress, product));
	}

	@Transactional(readOnly = true)
	public List<BannerRead.Response> allBanners() {
		List<Banner> banners = bannerRepository.findAll();
		return banners.stream()
				.map(BannerRead.Response::of)
				.collect(Collectors.toList());
	}

}
