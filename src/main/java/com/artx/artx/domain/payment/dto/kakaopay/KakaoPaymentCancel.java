package com.artx.artx.domain.payment.dto.kakaopay;

import com.artx.artx.domain.payment.dto.PaymentCancel;
import lombok.Getter;

@Getter
public class KakaoPaymentCancel {
	public static class Response extends PaymentCancel.Response {
		private String aid;
		private String tid;
		private String cid;
		private String status;
		private String partner_order_id;
		private String partner_user_id;
		private String payment_method_type;
		private Amount amount;
		private ApprovedCancelAmount approved_cancel_amount;
		private CanceledAmount canceled_amount;
		private CancelAvailableAmount cancel_available_amount;
		private String item_name;
		private String item_code;
		private Integer quantity;
		private String created_at;
		private String approved_at;
		private String canceled_at;
		private String payload;
	}
}
