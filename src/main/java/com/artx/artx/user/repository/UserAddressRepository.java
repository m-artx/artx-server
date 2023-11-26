package com.artx.artx.user.repository;

import com.artx.artx.user.entity.UserAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

	@Query("SELECT a FROM UserAddress a WHERE a.user.userId = :userId")
	Page<UserAddress> findAllByUser_UserId(UUID userId, Pageable pageable);

	boolean existsByUser_UserIdAndId(UUID userId, Long userAddressId);

}
