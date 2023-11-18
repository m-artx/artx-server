package com.artx.artx.order.repository;

import com.artx.artx.order.entity.Order;
import com.artx.artx.order.type.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("SELECT o FROM Order o " +
			"LEFT JOIN FETCH o.orderProducts op " +
			"LEFT JOIN FETCH o.delivery " +
			"WHERE o.user.userId = :userId " +
			"ORDER BY o.createdAt DESC")
	Page<Order> fetchByUserIdWithOrderProductsAndDelivery(@Param("userId") UUID userId, Pageable pageable);


	@Query(
			"SELECT o FROM Order o " +
			"WHERE o.id = :orderId AND o.user.userId = :userId"
	)
	Optional<Order> fetchByUserIdWithPayment(@Param("userId") UUID userId, @Param("orderId") String orderId);


	@Modifying
	@Query("UPDATE Order o SET o.status = :afterStatus WHERE o.createdAt < :sevenDaysAgo AND o.status = :beforeStatus")
	void updateExpiredOrderToCancel(@Param("sevenDaysAgo")LocalDateTime sevenDaysAgo, @Param("beforeStatus") OrderStatus beforeStatus, OrderStatus afterStatus);


	@Query("SELECT o.status, count(o) FROM Order o GROUP BY o.status")
	List<Object[]> countAllOrderStatus();

	@Query(
			"SELECT MONTH (o.createdAt), count(o) FROM Order o " +
			"WHERE o.createdAt BETWEEN :startDateTime AND :endDateTime " +
					"GROUP BY MONTH (o.createdAt) " +
					"ORDER BY MONTH (o.createdAt) "
	)
	List<Object[]> countAllMontlyOrder(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);


	@Query(
			"SELECT YEAR (o.createdAt), count(o) FROM Order o " +
					"WHERE o.createdAt BETWEEN :startDateTime AND :endDateTime " +
					"GROUP BY YEAR (o.createdAt) " +
					"ORDER BY YEAR (o.createdAt) "
	)
	List<Object[]> countAllYearlyOrder(LocalDateTime startDateTime, LocalDateTime endDateTime);


//	@Query(
//			"SELECT YEAR (o.createdAt), MONTH (o.createdAt), count(o) FROM Order o " +
//					"WHERE o.createdAt BETWEEN :startDate AND :endDate " +
//					"GROUP BY YEAR (o.createdAt), MONTH (o.createdAt) " +
//					"ORDER BY YEAR (o.createdAt), MONTH (o.createdAt)"
//	)
//	long getAllMontlyOrderCounts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
