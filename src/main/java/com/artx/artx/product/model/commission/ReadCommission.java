//package com.artx.artx.product.model.commission;
//
//import com.artx.artx.product.type.CommissionStatus;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
//public class ReadCommission {
//
//	public static class Request {
//
//	}
//
//	@Getter
//	public static class Response {
//		private String artistNickname;
//		private String commissionTitle;
//		private String commissionContent;
//		private CommissionStatus commissionStatus;
//		private LocalDateTime commissionCreatedAt;
//	}
//
//	@Getter
//	@Builder
//	public static class ResponseAll {
//		private String artistNickname;
//		private Long commissionProductId;
//		private String commissionProductRepresentativeImage;
//		private String commissionLink;
//		private String commissionTitle;
//		private String commissionOrderTotalAmount;
//		private CommissionStatus commissionStatus;
//		private LocalDateTime commissionCreatedAt;
//	}
//
//}
