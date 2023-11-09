package com.artx.artx.payment.service;

import com.artx.artx.order.entity.Order;
import com.artx.artx.payment.entity.Payment;
import com.artx.artx.payment.model.CancelPayment;
import com.artx.artx.payment.model.CreatePayment;

public interface PaymentService {

	CreatePayment.ReadyResponse readyPayment(Order order);
	CancelPayment cancelPayment(Payment payment);

}
