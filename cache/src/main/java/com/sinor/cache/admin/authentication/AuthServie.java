package com.sinor.cache.admin.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServie {
	private final WebClient webClient;
	/**
	 * authorization : base64 encode (clientId:clientSecret) - 인증서버에서 발급받은 clientId와 clientSecret을 base64로 인코딩한 값
	 *
	 */
	@Value("${jwt.authorization}")
	private String Authorization;

	/**
	 * username : 인증서버에서 저장한 사용자 아이디,비밀번호
	 */
	@Value("${jwt.username}")
	private String username;
	@Value("${jwt.password}")
	private String password;

	public Mono<AuthenticationDto.Res> getAccessToken() {
		return webClient.post()
			.uri("http://authHost:9000/api/v1/authentication")
			.header("Authorization", "Basic " + Authorization)
			.bodyValue(new AuthenticationDto.Req(username, password))
			.retrieve()
			.bodyToMono(AuthenticationDto.Res.class);
	}
}
