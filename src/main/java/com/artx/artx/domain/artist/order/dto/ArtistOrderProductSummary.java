package com.artx.artx.domain.artist.order.dto;

import com.artx.artx.domain.order.domain.OrderProduct;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class ArtistOrderProductSummary {

	private Long productId;
	private Long orderProductQuantity;
	private Long orderProductPrice;

	public static ArtistOrderProductSummary of(OrderProduct orderProduct){

		return ArtistOrderProductSummary.builder()
				.productId(Objects.isNull(orderProduct.getProduct()) ? null : orderProduct.getProduct().getId())
				.orderProductQuantity(orderProduct.getQuantity())
				.orderProductPrice(Objects.isNull(orderProduct.getProduct()) ? null : orderProduct.getProduct().getPrice())
				.build();
	}

}
