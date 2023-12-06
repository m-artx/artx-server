package com.artx.artx.domain.order.dto.detail;

import com.artx.artx.domain.order.domain.OrderProduct;
import com.artx.artx.domain.product.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductDetail {

	private Long productId;

	@NotBlank
	private String productTitle;

	@NotBlank
	private String productRepresentativeImage;

	@NotNull
	private Long productPrice;

	@NotNull
	private Long productQuantity;

	@NotNull
	private Long productTotalAmount;

	public static OrderProductDetail of(OrderProduct orderProduct){
		Product product = orderProduct.getProduct();

		if(orderProduct == null || product == null){
			return null;
		}

		return OrderProductDetail.builder()
				.productId(product.getId())
				.productTitle(product.getTitle())
				.productRepresentativeImage(product.getRepresentativeImage())
				.productPrice(product.getPrice())
				.productQuantity(orderProduct.getQuantity())
				.productTotalAmount(product.getPrice() * orderProduct.getQuantity())
				.build();
	}

}
