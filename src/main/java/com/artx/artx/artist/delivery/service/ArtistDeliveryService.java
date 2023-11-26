package com.artx.artx.artist.delivery.service;

import com.artx.artx.artist.delivery.model.ArtistDeliveryCreate;
import com.artx.artx.delivery.entity.Delivery;
import com.artx.artx.delivery.repository.DeliveryRepository;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistDeliveryService {

	private final DeliveryRepository deliveryRepository;

	@Transactional
	public ArtistDeliveryCreate.Response updateDelivery(String deliveryId, ArtistDeliveryCreate.Request request) {
		Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));
		delivery.setTrackingNumber(request.getDeliveryTrackingNumber());
		delivery.setCompany(request.getDeliveryCompany());
		delivery.changeStatus(request.getDeliveryStatus());
		return ArtistDeliveryCreate.Response.of(delivery.getModifiedAt());
	}
}
