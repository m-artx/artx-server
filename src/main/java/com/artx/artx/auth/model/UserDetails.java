package com.artx.artx.auth.model;

import com.artx.artx.user.type.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetails extends User {

	private final UUID userId;
	private final String username;
	private final String password;
	private final UserRole userRole;

	public UserDetails(UUID userId, String username, String password, UserRole userRole) {
		super(username, password, true, true, true, true, getSimpleGrantedAuthority(userRole));
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	}

	private static Collection<? extends GrantedAuthority> getSimpleGrantedAuthority(UserRole userRole) {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
	}

	public UUID getUserId() {
		return userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public UserRole getUserRole() {
		return userRole;
	}
}
