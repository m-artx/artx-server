package com.artx.artx.artist.service;

import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.order.service.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArtistOrderService {

	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;

//	public ArtistOrderRead.ResponseAll readAllOrders(UUID userId, Pageable pageable){
//		Page<Order> orders = orderRepository.findAllByArtistId(userId, pageable);
//
//		return orders.map()
//	}
}
