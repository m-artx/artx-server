package com.artx.artx.order.model;

import com.artx.artx.order.entity.OrderProduct;
import com.artx.artx.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetail {

	private Long productId;
	private String productTitle;
	private String productRepresentativeImage;
	private Long productPrice;
	private Long productQuantity;
	private Long productTotalPrice;

	public static OrderDetail from(OrderProduct orderProduct){
		Product product = orderProduct.getProduct();
		return OrderDetail.builder()
				.productId(product.getId())
				.productTitle(product.getTitle())
				.productRepresentativeImage(product.getRepresentativeImage())
				.productPrice(product.getPrice())
				.productQuantity(orderProduct.getQuantity())
				.productTotalPrice(product.getPrice() * orderProduct.getQuantity())
				.build();
	}

}
