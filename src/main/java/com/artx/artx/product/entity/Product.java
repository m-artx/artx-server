package com.artx.artx.product.entity;

import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.product.model.CreateProduct;
import com.artx.artx.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
	private String commissionNotice;
	private long price;
	private long views;
	private boolean isDeleted;

	//fetch lazy(X)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_stock_id")
	private ProductStock productStock;

	public static Product from(CreateProduct.Request request){
		return Product.builder()
				.title(request.getProductTitle())
				.description(request.getProductDescription())
				.price(request.getProductPrice())
				.isDeleted(false)
				.build();
	}

	//TODO: list 개수 제한 필요
	public void addProductImage(ProductImage productImage){
		if(this.productImages == null){
			this.productImages = new ArrayList<>();
		}
		productImages.add(productImage);
	}

	public void setProductImages(List<MultipartFile> multipartFiles) {
		if(multipartFiles != null && multipartFiles.size() > 0){

			List<ProductImage> productImages = multipartFiles.stream()
					.map(ProductImage::from)
					.peek(productImage -> productImage.setProduct(this))
					.collect(Collectors.toList());

			if(productImages != null && productImages.size() > 0){
				this.representativeImage = productImages.get(0).getName();
			}

			this.productImages = productImages;
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
}
