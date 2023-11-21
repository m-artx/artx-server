package com.artx.artx.user.repository;

import com.artx.artx.user.entity.User;
import com.artx.artx.user.model.UserReadDto;
import com.artx.artx.user.type.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByUsername(String username);
	boolean existsByNickname(String nickname);
	boolean existsByEmail(String email);

	Optional<User> findByEmailAndUsername(String email, String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.cart c")
	Optional<User> findByIdWithCart(UUID userId);

	@Query("SELECT new com.artx.artx.user.model.UserReadDto(u.userId, u.nickname) FROM User u WHERE u.userRole = :userRole ORDER BY u.createdAt DESC")
	Page<UserReadDto> findNewUsersByUserRole(UserRole userRole, Pageable pageable);

	@Query("SELECT u.userRole, count(u) FROM User u GROUP BY u.userRole")
	List<Object[]> findAllDailyUserAndArtistCounts();

}
