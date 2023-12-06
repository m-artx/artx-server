package com.artx.artx.domain.artist.order.service;

import com.artx.artx.domain.artist.order.dto.ArtistOrderProductDetail;
import com.artx.artx.domain.artist.order.dto.ArtistOrderRead;
import com.artx.artx.domain.delivery.domain.Delivery;
import com.artx.artx.domain.delivery.dao.DeliveryRepository;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import com.artx.artx.domain.order.domain.Order;
import com.artx.artx.domain.order.dao.OrderProductRepository;
import com.artx.artx.domain.order.dao.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ArtistOrderService {

	private final OrderProductRepository orderProductRepository;
	private final OrderRepository orderRepository;
	private final DeliveryRepository deliveryRepository;
	@Value(value = "${api.artist-orders}")
	private String ordersApiAddress;

	@Transactional(readOnly = true)
	public Page<ArtistOrderRead.SummaryResponse> readAllOrders(UUID artistId, Pageable pageable){
		Page<Order> orders = orderRepository.fetchOrderWithOrderProductsByArtistId(artistId, pageable);


		return orders.map(ArtistOrderRead.SummaryResponse::of);
	}

	@Transactional(readOnly = true)
	public ArtistOrderRead.DetailResponse readOrderDetail(UUID sellerId, String orderId, Pageable pageable) {
		Page<ArtistOrderProductDetail> artistOrderProductDetails = orderProductRepository.fetchOrderProductsByUserId(sellerId, orderId, pageable).map(ArtistOrderProductDetail::of
		);
		Delivery delivery = deliveryRepository.fetchWithOrderByOrderId(sellerId, orderId).orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));

		return  ArtistOrderRead.DetailResponse.of(artistOrderProductDetails, delivery);
	}

}
