package com.artx.artx.customer.payment.service;

import com.artx.artx.customer.order.entity.Order;
import com.artx.artx.customer.order.entity.OrderProduct;
import com.artx.artx.customer.payment.model.kakaopay.KakaoPaymentCancel;
import com.artx.artx.customer.payment.model.kakaopay.KakaoPaymentCreate;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.customer.payment.entity.KakaoPayment;
import com.artx.artx.customer.payment.entity.Payment;
import com.artx.artx.customer.payment.model.PaymentCreate;
import com.artx.artx.customer.payment.model.PaymentRead;
import com.artx.artx.customer.payment.repository.KakaoPaymentRepository;
import com.artx.artx.customer.payment.repository.PaymentRepository;
import com.artx.artx.customer.payment.type.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoPayService implements PaymentService{

	private final RestTemplate restTemplate;
	private final KakaoPaymentRepository kakaoPaymentRepository;
	private final PaymentRepository paymentRepository;

	@Value("${kakaopay.key}")
	private String apiKey;

	@Value("${kakaopay.cid}")
	private String cid;

	@Value("${kakaopay.ready}")
	private String readyApiAddress;

	@Value("${kakaopay.approval}")
	private String approvalApiAddress;

	@Value("${kakaopay.cancel}")
	private String cancelApiAddress;

	@Value("${artx.address}")
	private String serverAddress;

	@Value(value = "${api.orders}")
	private String ordersApiAddress;

	@Override
	@Transactional
	public PaymentCreate.Response processPayment(Order order) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + apiKey);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", cid);
		params.add("partner_order_id", order.getId().toString());
		params.add("partner_user_id", order.getUser().getUserId().toString());
		params.add("item_name", order.generateOrderTitle());
		params.add("quantity", "1");
		params.add("total_amount", String.valueOf(order.generateTotalAmount()));
		params.add("tax_free_amount", "0");
		params.add("approval_url", serverAddress + "/api/payments/approval?partner_order_id=" + order.getId());
		params.add("cancel_url", serverAddress + "/api/payments/cancel");
		params.add("fail_url", serverAddress + "/api/payments/fail");

		HttpEntity request = new HttpEntity(params, headers);

		try {
			KakaoPaymentCreate.ReadyResponse readyResponse = restTemplate.postForObject(readyApiAddress, request, KakaoPaymentCreate.ReadyResponse.class);
			Payment payment = Payment.from(
					order,
					readyResponse.getTid()
			);

			payment.processPaymentSuccess();
			payment.setPaymentType(PaymentType.KAKAOPAY);

			KakaoPayment kakaoPayment = kakaoPaymentRepository.save(
					KakaoPayment.builder()
							.tid(readyResponse.getTid())
							.payment(payment)
							.build()
			);
			return readyResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.FAILED_KAKAOPAY_PAYMENT);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Page<PaymentRead.Response> readAllPayments(UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable){
		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
		LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay().plusDays(1L) : null;

		Page<Payment> payments = paymentRepository.findAllByUserIdWithOrder(userId, startDateTime, endDateTime, pageable);
		return payments.map(payment -> PaymentRead.Response.of(ordersApiAddress, payment));
	}

	@Transactional
	public KakaoPaymentCancel.Response cancelPayment(String orderId) {
		KakaoPayment kakaoPayment = kakaoPaymentRepository.fetchKakaoPaymentByOrderId(orderId).orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));
		Payment payment = kakaoPayment.getPayment();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + apiKey);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("cid", cid);
		params.add("tid", kakaoPayment.getTid());
		params.add("cancel_amount", String.valueOf(payment.getTotalAmount()));
		params.add("cancel_tax_free_amount", "0");

		HttpEntity request = new HttpEntity(params, headers);

		try {
			KakaoPaymentCancel.Response response = restTemplate.postForObject(cancelApiAddress, request, KakaoPaymentCancel.Response.class);
			payment.processPaymentCancel();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.CAN_NOT_PAYMENT_CANCEL);
		}
	}

	public KakaoPaymentCreate.ApprovalResponse approvalPayment(String orderId, String pgToken) {
		KakaoPayment kakaoPayment = kakaoPaymentRepository.fetchKakaoPaymentByOrderId(orderId).orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_NOT_FOUND));
		Payment payment = kakaoPayment.getPayment();
		Order order = payment.getOrder();

		List<OrderProduct> orderProducts = order.getOrderProducts();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + apiKey);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cid", cid);
		params.add("tid", kakaoPayment.getTid());
		params.add("partner_order_id", String.valueOf(order.getId()));
		params.add("partner_user_id", String.valueOf(order.getUser().getUserId()));
		params.add("pg_token", pgToken);

		try {
			HttpEntity request = new HttpEntity(params, headers);
			KakaoPaymentCreate.ApprovalResponse response = restTemplate.postForObject(approvalApiAddress, request, KakaoPaymentCreate.ApprovalResponse.class);

			payment.processPaymentSuccess();
			order.processOrderSuccess();
			orderProducts.stream().forEach(OrderProduct::decreaseOrderProductStocks);

			return response;
		} catch (Exception e) {
			payment.processPaymentFailure();
			order.processOrderFailure();
			throw new BusinessException(ErrorCode.FAILED_KAKAOPAY_PAYMENT);
		}

	}

}
