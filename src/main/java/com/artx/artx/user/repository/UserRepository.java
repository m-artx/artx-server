package com.artx.artx.user.repository;

import com.artx.artx.user.dto.UserArtist;
import com.artx.artx.user.model.User;
import com.artx.artx.user.type.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByUsername(String username);
	boolean existsByNickname(String nickname);
	boolean existsByEmail(String email);

	@Query("SELECT new com.artx.artx.user.dto.UserArtist(u.userId, u.nickname, u.userRole) FROM User u WHERE u.userRole = :userRole ORDER BY u.createdAt DESC")
	Page<UserArtist> getNewArtists(@Param("userRole") UserRole userRole, Pageable pageable);

}
