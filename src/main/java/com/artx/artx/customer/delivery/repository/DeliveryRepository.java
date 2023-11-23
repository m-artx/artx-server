package com.artx.artx.customer.delivery.repository;

import com.artx.artx.customer.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {

	@Query("SELECT d.status, count(d) FROM Delivery d GROUP BY d.status")
	List<Object[]> getAllDeliveryStatusCounts();

	@Query(
			"SELECT d FROM Delivery d " +
					"LEFT JOIN FETCH d.order do " +
					"WHERE d.order.id = :orderId"
	)
	Optional<Delivery> fetchWithOrderByOrderId(String orderId);

	@Query(
			"SELECT d FROM Delivery d " +
					"LEFT JOIN FETCH d.order do " +
					"WHERE d.order.id = :orderId AND d.user.userId = :sellerId"
	)
	Optional<Delivery> fetchWithOrderByOrderId(UUID sellerId, String orderId);

}
