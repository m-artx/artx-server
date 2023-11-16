package com.artx.artx.delivery.service;

import com.artx.artx.delivery.repository.DeliveryRepository;
import com.artx.artx.delivery.type.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;

	public Map<DeliveryStatus, Long> getAllDeliveryStatusCounts(){
		List<Object[]> statusCounts = deliveryRepository.getAllDeliveryStatusCounts();
		Map<DeliveryStatus, Long> map = new HashMap<>();

		for (Object[] statusCount : statusCounts) {
			map.put((DeliveryStatus) statusCount[0], (Long) statusCount[1]);
		}
		return map;
	}
}
