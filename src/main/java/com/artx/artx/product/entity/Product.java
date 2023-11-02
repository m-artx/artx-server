package com.artx.artx.product.entity;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.product.model.ProductRequest;
import com.artx.artx.user.model.User;
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
	private Long price;
	private long quantity;

	public static Product from(ProductRequest.Create request, ProductCategory productCategory, User user){
		return Product.builder()
				.user(user)
				.productCategory(productCategory)
				.title(request.getProductTitle())
				.description(request.getProductDescription())
				.price(request.getProductPrice())
				.quantity(request.getProductQuantity())
				.build();
	}

	public void increase(long quantity) {
		if(quantity <= 0){
			throw new BusinessException(ErrorCode.MUST_BE_MORE_THAN_ZERO);
		}
		this.quantity += quantity;
	}

	public void decrease(long quantity) {
		if(quantity <= 0){
			throw new BusinessException(ErrorCode.MUST_BE_MORE_THAN_ZERO);
		}

		if(this.quantity - quantity < 0){
			throw new BusinessException(ErrorCode.CAN_NOT_DECREASE);
		}

		this.quantity -= quantity;
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
}
