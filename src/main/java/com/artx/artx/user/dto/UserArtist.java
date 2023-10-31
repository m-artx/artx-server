package com.artx.artx.user.dto;

import com.artx.artx.user.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserArtist {
	private UUID userId;
	private String nickname;
	private UserRole userRole;
}
