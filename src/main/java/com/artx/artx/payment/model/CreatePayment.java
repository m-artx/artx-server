package com.artx.artx.payment.model;

import lombok.Getter;

import java.time.LocalDateTime;

public class CreatePayment {

	@Getter
	public static class Request {
	}

	@Getter
	public static class ReadyResponse {
		String tid;
		String next_redirect_app_url;
		String next_redirect_mobile_url;
		String next_redirect_pc_url;
		String android_app_scheme;
		String ios_app_scheme;
		LocalDateTime created_at;
	}

	@Getter
	public static class ApprovalResponse {
		private String aid; // 요청 고유 번호
		private String tid; // 결제 고유 번호
		private String cid; // 가맹점 코드
		private String sid; // 정기결제용 ID
		private String partner_order_id; // 가맹점 주문 번호
		private String partner_user_id; // 가맹점 회원 id
		private String payment_method_type; // 결제 수단
		private Amount amount; // 결제 금액 정보
		private String item_name; // 상품명
		private String item_code; // 상품 코드
		private Long quantity; // 상품 수량
		private String created_at; // 결제 요청 시간
		private String approved_at; // 결제 승인 시간
		private String payload;// 결제 승인 요청에 대해 저장 값, 요청 시 전달 내용
	}

	@Getter
	public static class Amount {
		private Long total;
		private Long tax_free;
		private Long vat;
		private Long point;
		private Long discount;
		private Long green_deposi;
	}
}
