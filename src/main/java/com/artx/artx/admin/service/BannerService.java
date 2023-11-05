package com.artx.artx.admin.service;

import com.artx.artx.admin.entity.Banner;
import com.artx.artx.admin.model.CreateBanner;
import com.artx.artx.admin.model.ReadBanner;
import com.artx.artx.admin.repository.BannerRepository;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
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

	@Value(value = "${api.products}")
	private String productsApiAddress;

	@Transactional
	public void createBanner(CreateBanner.Request request) {
		Product product = productService.getProductById(request.getProductId());
		if (bannerRepository.existsByProduct(product)) {
			throw new BusinessException(ErrorCode.DUPLICATED_BANNER);
		}
		bannerRepository.save(Banner.from(productsApiAddress, product));
	}

	@Transactional(readOnly = true)
	public List<ReadBanner.Response> allBanners() {
		List<Banner> banners = bannerRepository.findAll();
		return banners.stream()
				.map(ReadBanner.Response::from)
				.collect(Collectors.toList());
	}

}
