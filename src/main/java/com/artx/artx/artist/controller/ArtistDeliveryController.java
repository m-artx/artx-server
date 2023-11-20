package com.artx.artx.artist.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "작가 배송 관리")
@RestController
@RequestMapping("/api/artist/delivery")
@RequiredArgsConstructor
@Secured("ROLE_ARTIST")
public class ArtistDeliveryController {

	//TODO: 택배 예약(주소, 낮 12시 택배 수거 후 운송장 발급, 배송 시작)
	//TODO: 배송 스케쥴링 (1일차 4시에 배송 중, 2일차 13시 배송 완료)
	//TODO: 이메일로 택배 완료 문자 전송





}
