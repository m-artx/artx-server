package com.artx.artx.order.model.detail;

import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductDetail {

	private Long productId;
	private String productTitle;
	private String productRepresentativeImage;
	private Long productPrice;
	private Long productQuantity;
	private Long productTotalAmount;

	public static OrderProductDetail of(OrderProduct orderProduct){
		Product product = orderProduct.getProduct();
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
