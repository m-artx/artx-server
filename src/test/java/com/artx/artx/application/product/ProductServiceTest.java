//package com.artx.artx.application.product;
//
//import com.artx.artx.domain.product.application.ProductService;
//import com.artx.artx.domain.product.dao.ProductRepository;
//import com.artx.artx.domain.product.domain.Product;
//import com.artx.artx.domain.product.domain.ProductCategoryType;
//import com.artx.artx.domain.product.domain.ProductStock;
//import com.artx.artx.domain.product.dto.ProductUpdate;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//public class ProductServiceTest {
//
//	@Autowired
//	private ProductService productService;
//
//	@Autowired
//	private ProductRepository productRepository;
//
//	@DisplayName(value = "상품 등록을 성공한다.")
//	@Test
//	void createProduct(){
//		//given
//		Product product1 = createProduct("TITLE1", "DESCRIPTION", 100L);
//		Product product2 = createProduct("TITLE2", "DESCRIPTION", 200L);
//		Product product3 = createProduct("TITLE3", "DESCRIPTION", 300L);
//
//		//when
//		productRepository.saveAll(List.of(product1, product2, product3));
//	}
//
//	@DisplayName(value = "상품 수정을 성공한다.")
//	@Test
//	void updateProduct(){
//		//given
//		Product product = createProduct("TITLE", "DESCRIPTION", 100L);
//
//		ProductUpdate.Request request = ProductUpdate.Request.builder()
//				.productCategory(ProductCategoryType.PAINT)
//				.productTitle("TITLE2")
//				.productDescription("설명")
//				.productPrice(1000L)
//				.productStockQuantity(100L)
//				.build();
//
//		//when
//		product.update(request);
//
//		//then
//		assertThat(product.getTitle()).isEqualTo("TITLE2");
//		assertThat(product.getProductStock().getQuantity()).isEqualTo(100L);
//
//	}
//
//	Product createProduct(String title, String description, Long quantity){
//
//		Product product = Product.builder()
//				.title(title)
//				.description(description)
//				.build();
//
//		ProductStock productStock = ProductStock.builder()
//				.product(product)
//				.quantity(quantity)
//				.build();
//
//		product.setProductStock(productStock);
//
//		return product;
//	}
//
//
//
//}
