package com.artx.artx.artist.delivery.model;

import com.artx.artx.delivery.type.DeliveryStatus;
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
