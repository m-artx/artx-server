package com.artx.artx.artist.order.model;

import com.artx.artx.customer.order.entity.OrderProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistOrderProductSummary {

	private Long productId;
	private Long orderProductQuantity;
	private Long orderProductPrice;

	public static ArtistOrderProductSummary of(OrderProduct orderProduct){
		return ArtistOrderProductSummary.builder()
				.productId(orderProduct.getProduct().getId())
				.orderProductQuantity(orderProduct.getQuantity())
				.orderProductPrice(orderProduct.getProduct().getPrice())
				.build();
	}

}
