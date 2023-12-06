package com.artx.artx.domain.product.domain;

import com.artx.artx.domain.product.dto.ProductCreate;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "productStock")
	private Product product;

	@Version
	private Long quantity;

	public static ProductStock from(ProductCreate.Request request){
		return ProductStock.builder()
				.quantity(request.getProductStockQuantity())
				.build();
	}

	public void increase(long quantity) {
		if(quantity <= 0){
			throw new BusinessException(ErrorCode.MUST_BE_MORE_THAN_ZERO);
		}
		this.quantity += quantity;
	}

	public void decrease(long quantity) {
		isQuantityLessThan(quantity);
		this.quantity -= quantity;
	}

	public boolean isQuantityLessThan(long quantity){
		if(this.quantity < quantity){
			throw new BusinessException(ErrorCode.CAN_NOT_DECREASE);
		}
		return true;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void updateQuantity(Long productQuantity) {
		this.quantity = productQuantity;
	}
}
