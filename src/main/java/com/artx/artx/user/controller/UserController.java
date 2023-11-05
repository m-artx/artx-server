package com.artx.artx.user.controller;

import com.artx.artx.user.model.CreateUser;
import com.artx.artx.user.model.ReadUser;
import com.artx.artx.user.model.ReadUserDto;
import com.artx.artx.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원가입", description = "가입 정보와 함께 회원가입을 할 수 있다.")
	@PostMapping
	public ResponseEntity<CreateUser.Response> create(@RequestBody CreateUser.Request request){
		CreateUser.Response response = userService.createUser(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "특정 유저 조회", description = "특정 유저를 조회할 수 있다.")
	@GetMapping("/{userId}")
	public ResponseEntity<ReadUser.Response> read(@PathVariable UUID userId){
		ReadUser.Response response = userService.readUser(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "신규 가입 작가 조회", description = "새로 가입한 작가들을 조회할 수 있다.")
	@GetMapping("/new-artist")
	public ResponseEntity<Page<ReadUserDto>> readNewArtists(Pageable pageable){
		return ResponseEntity.ok(userService.getNewArtists(pageable));
	}

}
