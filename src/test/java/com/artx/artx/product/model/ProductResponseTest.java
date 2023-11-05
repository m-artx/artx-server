package com.artx.artx.product.model;

import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductImage;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductResponseTest {

	@Test
	void stringBuilderTest(){
		Product product = Product.builder().id(1L).price(10000L).productImages(List.of(ProductImage.builder().name("GET.png").build())).build();
		ProductResponse.Read from = ProductResponse.Read.from("http://localhost:8080/", product);
		System.out.println(from.getProductImages().get(0));
	}

}