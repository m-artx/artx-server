package com.artx.artx.common.user.repository;

import com.artx.artx.common.user.type.UserRole;
import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.model.UserReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

	Optional<User> findByEmailAndUsername(String email, String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);

	@Query("SELECT new com.artx.artx.common.user.model.UserReadDto(u.userId, u.nickname) FROM User u WHERE u.userRole = :userRole ORDER BY u.createdAt DESC")
	Page<UserReadDto> findUsersByUserRole(UserRole userRole, Pageable pageable);

	@Query("SELECT u.userRole, count(u) FROM User u GROUP BY u.userRole")
	List<Object[]> findAllDailyUserAndArtistCounts();


	@Query("SELECT u FROM User u LEFT JOIN FETCH u.userAddresses ua WHERE u.userId = :userId")
	Optional<User> findWithAddressById(UUID userId);

}
