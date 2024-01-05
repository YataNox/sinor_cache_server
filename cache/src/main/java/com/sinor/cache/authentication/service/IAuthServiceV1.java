package com.sinor.cache.authentication.service;

import com.sinor.cache.authentication.model.AuthResponse;

import reactor.core.publisher.Mono;

public interface IAuthServiceV1 {
	/**
	 * @apiNote 인증서버의 /api/v1/authentication api를 호출하여 access token을 받아온다.
	 * @return
	 */
	Mono<AuthResponse.Res> getAccessToken(AuthResponse.Req req);
}
