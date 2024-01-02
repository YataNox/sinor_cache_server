package com.sinor.cache.admin.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthServie AuthServie;

	@GetMapping("/api/v1/authentication")
	public Mono<AuthenticationDto.Res> authentication() {
		return AuthServie.getAccessToken();
	}

	@GetMapping("/admin/test")
	public String test() {
		return "admin user";
	}

	@GetMapping("/user/test")
	public String test2() {
		return "user user";
	}
}
