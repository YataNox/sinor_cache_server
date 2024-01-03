package com.sinor.cache.authentication.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.authentication.model.AuthResponse;
import com.sinor.cache.authentication.service.AuthServie;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController implements IAuthControllerV1 {
	private final AuthServie authServie;

	public Mono<AuthResponse.Res> authentication(AuthResponse.Req req) {
		return authServie.getAccessToken(req);
	}

	public String test() {
		return "admin test";
	}
}
