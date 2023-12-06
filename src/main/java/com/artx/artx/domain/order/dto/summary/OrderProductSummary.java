package com.artx.artx.domain.order.dto.summary;

import com.artx.artx.domain.order.domain.OrderProduct;
import com.artx.artx.domain.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductSummary {

	private Long productId;
	private String productTitle;
	private String productRepresentativeImage;

	public static OrderProductSummary of(OrderProduct orderProduct){
		Product product = orderProduct.getProduct();
		return OrderProductSummary.builder()
				.productId(product.getId())
				.productTitle(product.getTitle())
				.productRepresentativeImage(product.getRepresentativeImage())
				.build();
	}
}
