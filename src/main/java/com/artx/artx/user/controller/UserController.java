package com.artx.artx.user.controller;

import com.artx.artx.user.dto.UserArtist;
import com.artx.artx.user.dto.UserRequest;
import com.artx.artx.user.dto.UserResponse;
import com.artx.artx.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse.Create> create(@RequestBody UserRequest.Create request){
		UserResponse.Create response = userService.createUser(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse.Read> read(@PathVariable UUID userId){
		UserResponse.Read response = userService.readUser(userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/new-artist")
	public ResponseEntity<Page<UserArtist>> readNewArtists(Pageable pageable){
		return ResponseEntity.ok(userService.getNewArtists(pageable));
	}

}
