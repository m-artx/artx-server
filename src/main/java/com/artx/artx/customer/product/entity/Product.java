package com.artx.artx.customer.product.entity;

import com.artx.artx.customer.product.model.ProductCreate;
import com.artx.artx.common.user.entity.User;
import com.artx.artx.etc.model.BaseEntity;
import com.artx.artx.customer.product.model.ProductUpdate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_category_id", nullable = false)
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> productImages;

	private String representativeImage;
	private String title;
	private String description;
	private String commissionNotice;
	private long price;
	private long views;
	private boolean isDeleted;

	//fetch lazy(X)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_stock_id")
	private ProductStock productStock;

	public static Product from(ProductCreate.Request request, User user, ProductCategory productCategory, ProductStock productStock) {
		return Product.builder()
				.title(request.getProductTitle())
				.description(request.getProductDescription())
				.price(request.getProductPrice())
				.user(user)
				.productCategory(productCategory)
				.productStock(productStock)
				.isDeleted(false)
				.build();
	}

	public void update(ProductUpdate.Request request) {
		this.title = request.getProductTitle();
		this.description = request.getProductDescription();
		this.price =  request.getProductPrice();
		this.productStock.setQuantity(request.getProductStockQuantity());
	}

	public void setProductImages(List<String> productImages) {
		if (productImages != null && productImages.size() > 0) {
			this.representativeImage = productImages.get(0);
			List<ProductImage> images = productImages.stream()
					.map(ProductImage::from)
					.collect(Collectors.toList());

			images.stream().forEach(productImage -> productImage.setProduct(this));
			this.productImages = images;
		}
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

	public void updatePrice(Long productPrice) {
		this.price = productPrice;
	}
}
