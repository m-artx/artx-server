package com.artx.artx.domain.artist.delivery.dto;

import com.artx.artx.domain.delivery.domain.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ArtistDeliveryCreate {

	@Getter
	@Builder
	public static class Request {
		private String deliveryTrackingNumber;
		private String deliveryCompany;
		private DeliveryStatus deliveryStatus;

	}

	@Getter
	@Builder
	public static class Response {
		private LocalDateTime deliveryUpdatedAt;


		public static Response of(LocalDateTime deliveryUpdatedAt){
			return Response.builder()
					.deliveryUpdatedAt(deliveryUpdatedAt)
					.build();
		}
	}
}
