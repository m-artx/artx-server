package com.artx.artx.banner.service;

import com.artx.artx.banner.model.BannerRequest;
import com.artx.artx.banner.model.BannerResponse;
import com.artx.artx.banner.repository.BannerRepository;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.banner.entity.Banner;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {
	private final ProductService productService;
	private final BannerRepository bannerRepository;

	@Value(value = "${server.full-url}")
	private String serverUrl;

	@Transactional
	public void addBanner(BannerRequest.Create request) {
		Product product = productService.getProductById(request.getProductId());
		if(bannerRepository.existsByProduct(product)){
			throw new BusinessException(ErrorCode.DUPLICATED_BANNER);
		}

		StringBuilder sb = new StringBuilder();

		bannerRepository.save(
				Banner.builder()
						.product(productService.getProductById(request.getProductId()))
						.link(sb.append(serverUrl).append("/api/products/").append(product.getId().toString()).toString())
						.build()
		);
	}

	@Transactional(readOnly = true)
	public List<BannerResponse> allBanners() {
		List<Banner> banners = bannerRepository.findAll();
		return banners.stream().map(BannerResponse::from).collect(Collectors.toList());
	}

}
