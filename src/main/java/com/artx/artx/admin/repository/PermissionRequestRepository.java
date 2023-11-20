package com.artx.artx.admin.repository;

import com.artx.artx.admin.entity.PermissionRequest;
import com.artx.artx.admin.type.PermissionRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PermissionRequestRepository extends JpaRepository<PermissionRequest, Long> {

	@Query("SELECT p FROM PermissionRequest p WHERE p.user.userId = :userId")
	Page<PermissionRequest> findPermissionRequestByUser_UserId(UUID userId, Pageable pageable);

	@Query("SELECT p FROM PermissionRequest p WHERE p.status = :permissionRequestStatus")
	Page<PermissionRequest> findAllPermissionRequestPageByStatus(PermissionRequestStatus permissionRequestStatus, Pageable pageable);
}
