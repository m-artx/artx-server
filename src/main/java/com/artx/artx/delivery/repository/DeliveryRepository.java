package com.artx.artx.delivery.repository;

import com.artx.artx.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {

	@Query("SELECT d.status, count(d) FROM Delivery d GROUP BY d.status")
	List<Object[]> getAllDeliveryStatusCounts();

	@Query("SELECT d FROM Delivery d WHERE d.order.id = :orderId")
	Optional<Delivery> findByOrderIdAndArtistId(String orderId);

}
