package com.artx.artx.product.entity;

import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.product.model.CreateProduct;
import com.artx.artx.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImage> productImages;

	private String representativeImage;
	private String title;
	private String description;
	private long price;
	private long views;

	//fetch lazy(X)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_stock_id")
	private ProductStock productStock;

	public static Product from(CreateProduct.Request request){
		return Product.builder()
				.title(request.getProductTitle())
				.description(request.getProductDescription())
				.price(request.getProductPrice())
				.build();
	}




	//TODO: list 개수 제한 필요
	public void addProductImage(ProductImage productImage){
		if(this.productImages == null){
			this.productImages = new ArrayList<>();
		}
		productImages.add(productImage);
	}

	public void saveProductImages(List<ProductImage> productImages) {
		productImages.stream().forEach(productImage -> productImage.setProduct(this));
		this.productImages = productImages;
	}

	public void setReresentativeImage(String fileName) {
		this.representativeImage = fileName;
	}

	public void setViews(long views) {
		this.views = views;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public void setProductStock(ProductStock productStock) {
		this.productStock = productStock;
	}

	public void setCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
}
