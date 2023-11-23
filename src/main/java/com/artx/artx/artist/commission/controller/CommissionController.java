package com.artx.artx.artist.commission.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "아티스트")
@RestController
@RequestMapping("/api/artist/commissions")
@RequiredArgsConstructor
@Secured("ROLE_ARTIST")
public class CommissionController {
}
