//package com.artx.artx.artist.product.controller;
//
//import com.artx.artx.artist.product.model.ArtistProductPriceUpdate;
//import com.artx.artx.artist.product.model.ArtistProductQuantityUpdate;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/api/artist/products")
//@RequiredArgsConstructor
//public class ArtistProductStockController {
//
//	private final ArtistProductStockService artistProductStockService;
//
//	@PatchMapping("/{productId}/quantity")
//	public void updateProductQuantity(
//			@PathVariable Long productId,
//			@RequestBody ArtistProductQuantityUpdate.Request request
//	){
//		artistProductStockService.updateProductQuantity(productId, request);
//	}
//
//	@PatchMapping("/{productId}/price")
//	public void updateProductPrice(
//			@PathVariable Long productId,
//			@RequestBody ArtistProductPriceUpdate.Request request
//	){
//		artistProductStockService.updateProductPrice(productId, request);
//	}
//}
