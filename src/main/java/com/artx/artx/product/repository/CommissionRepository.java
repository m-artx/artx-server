//package com.artx.artx.product.repository;
//
//import com.artx.artx.product.entity.Commission;
//import com.artx.artx.product.entity.CommissionId;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.UUID;
//
//public interface CommissionRepository extends JpaRepository<Commission, CommissionId> {
//
//
//	@Query(
//			"SELECT c FROM Commission c " +
//					"LEFT JOIN FETCH c.user cu " +
//					"LEFT JOIN FETCH c.product cp " +
//					"WHERE c.user.userId = :userId"
//	)
//	Page<Commission> findAllCommissionsByUserIdWithUserAndProduct(@Param("userId") UUID userId, Pageable pageable);
//}
