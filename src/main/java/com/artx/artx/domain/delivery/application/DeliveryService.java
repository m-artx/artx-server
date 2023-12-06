package com.artx.artx.domain.delivery.application;

import com.artx.artx.domain.delivery.dao.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;

//	public List<Delivery> getDeliveryByOrderId(String orderId) {
//		return deliveryRepository.findAllByOrder_Id(orderId);
//	}
}
