package com.artx.artx.domain.admin.permission.repository;

import com.artx.artx.domain.admin.permission.entity.PermissionRequest;
import com.artx.artx.domain.admin.permission.type.PermissionRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRequestRepository extends JpaRepository<PermissionRequest, Long> {

	@Query("SELECT p FROM PermissionRequest p WHERE p.user.userId = :userId")
	Page<PermissionRequest> findPermissionRequestByUser_UserId(UUID userId, Pageable pageable);

	@Query("SELECT p FROM PermissionRequest p LEFT JOIN FETCH p.user WHERE p.status = :permissionRequestStatus")
	Page<PermissionRequest> findAllPermissionRequestPageByStatus(PermissionRequestStatus permissionRequestStatus, Pageable pageable);

	@Query("SELECT p FROM PermissionRequest p LEFT JOIN FETCH p.user WHERE p.id = :permissionRequestId")
	Optional<PermissionRequest> findWithUserById(Long permissionRequestId);

	Optional<PermissionRequest> findByUser_UserId(UUID userId);
	boolean existsByUser_UserId(UUID userId);
}
