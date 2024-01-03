package com.sinor.cache.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.authentication.model.AuthResponse;

import reactor.core.publisher.Mono;

@RestController
public interface IAuthControllerV1 {
	/**
	 * @apiNote 인증서버의 /api/v1/authentication api를 호출하여 access token을 받아온다.
	 * @return
	 */
	@PostMapping("/api/v1/authentication")
	Mono<AuthResponse.Res> authentication(AuthResponse.Req req);

	@GetMapping("/admin/test")
	String test();
}
