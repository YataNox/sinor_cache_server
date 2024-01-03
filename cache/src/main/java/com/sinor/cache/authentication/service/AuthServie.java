package com.sinor.cache.authentication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.sinor.cache.authentication.model.AuthResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServie implements IAuthServieV1 {

	@Value("${jwt.authorization}")
	private String Authorization;
	private final WebClient webClient;

	@Override
	public Mono<AuthResponse.Res> getAccessToken(@RequestBody AuthResponse.Req req) {
		System.out.println("req : " + req.getUsername() + ", " + req.getPassword());
		return webClient.post()
			.uri("http://authHost:9000/api/v1/authentication")
			.header("Authorization", "Basic " + Authorization)
			.contentType(MediaType.APPLICATION_JSON) // Content-Type 설정
			.bodyValue(req)
			.retrieve()
			.bodyToMono(AuthResponse.Res.class);
	}
}
