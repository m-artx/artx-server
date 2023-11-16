package com.artx.artx.delivery.repository;

import com.artx.artx.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {

	@Query("SELECT d.status, count(d) FROM Delivery d GROUP BY d.status")
	List<Object[]> getAllDeliveryStatusCounts();
}
