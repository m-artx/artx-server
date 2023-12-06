package com.artx.artx.domain.artist.order.dto;

import com.artx.artx.domain.order.domain.OrderProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistOrderProductDetail {

	private Long productId;
	private Long orderProductQuantity;
	private Long orderProductPrice;
	private Long orderTotalPrice;

	public static ArtistOrderProductDetail of(OrderProduct orderProduct){
		return ArtistOrderProductDetail.builder()
				.productId(orderProduct.getProduct().getId())
				.orderProductQuantity(orderProduct.getQuantity())
				.orderProductPrice(orderProduct.getProduct().getPrice())
				.orderTotalPrice(orderProduct.getQuantity() * orderProduct.getProduct().getPrice())
				.build();
	}

}
