//package com.artx.artx.artist.product.service;
//
//import com.artx.artx.artist.product.model.ArtistProductPriceUpdate;
//import com.artx.artx.artist.product.model.ArtistProductQuantityUpdate;
//import com.artx.artx.customer.product.entity.Product;
//import com.artx.artx.customer.product.entity.ProductStock;
//import com.artx.artx.customer.product.repository.ProductRepository;
//import com.artx.artx.customer.product.repository.ProductStockRepository;
//import com.artx.artx.etc.error.ErrorCode;
//import com.artx.artx.etc.exception.BusinessException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class ArtistProductStockService {
//
//	private final ProductStockRepository productStockRepository;
//	private final ProductRepository productRepository;
//
//	@Transactional
//	public void updateProductPrice(Long productId, ArtistProductPriceUpdate.Request request) {
//		Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
//		product.updatePrice(request.getProductPrice());
//	}
//
//	@Transactional
//	public void updateProductQuantity(Long productId, ArtistProductQuantityUpdate.Request request) {
//
//		ProductStock productStock = productStockRepository.findByProduct_Id(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
//		productStock.updateQuantity(request.getProductQuantity());
//	}
//
//}
