//package com.artx.artx.product.model.commission;
//
//import com.artx.artx.product.entity.Commission;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public class CreateCommission {
//
//	@Getter
//	public static class Request {
//		private UUID userId;
//		private String content;
//	}
//
//	@Getter
//	@Builder
//	public static class Response {
//		private LocalDateTime createdAt;
//
//		public static Response of(Commission commission) {
//			return Response.builder()
//					.createdAt(commission.getCreatedAt())
//					.build();
//		}
//	}
//
//}
