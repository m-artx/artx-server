package com.artx.artx.product.entity;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.product.model.CreateProduct;
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

	public static ProductStock from(CreateProduct.Request request){
		return ProductStock.builder().quantity(request.getProductQuantity()).build();
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
	public boolean canDecrease(Long quantity) {
		if(quantity <= 0){
			throw new BusinessException(ErrorCode.MUST_BE_MORE_THAN_ZERO);
		}

		if(this.quantity - quantity < 0){
			throw new BusinessException(ErrorCode.CAN_NOT_DECREASE);
		}
		return true;
	}
}
