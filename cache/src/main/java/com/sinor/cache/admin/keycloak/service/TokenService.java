package com.sinor.cache.admin.keycloak.service;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

import com.sinor.cache.admin.keycloak.model.TokenResponse;
import com.sinor.cache.config.KeyCloakConfig;

@Service
public class TokenService implements ITokenServiceV2 {
	private KeyCloakConfig keyCloakConfig;

	public TokenService(KeyCloakConfig keyCloakConfig) {
		this.keyCloakConfig = keyCloakConfig;
	}

	/**
	 * 일단 메서드화 -> 나중에 토큰 값만 따로 빼서 처리할 수 있다면 그렇게 할 예정.
	 * @param keyCloakConfig
	 * @return
	 */
	public TokenResponse setAcessTokenBuilder(KeyCloakConfig keyCloakConfig) {
		AccessTokenResponse accessTokenResponse = keyCloakConfig.keycloak().tokenManager().getAccessToken();
		if (accessTokenResponse != null) {
			return TokenResponse.builder()
				.access_token(accessTokenResponse.getToken())
				.expires_in(accessTokenResponse.getExpiresIn())
				.refresh_token(accessTokenResponse.getRefreshToken())
				.refresh_expires_in(accessTokenResponse.getRefreshExpiresIn())
				.token_type(accessTokenResponse.getTokenType())
				.not_before_policy(accessTokenResponse.getNotBeforePolicy())
				.session_state(accessTokenResponse.getSessionState())
				.scope(accessTokenResponse.getScope())
				.build();
		} else {
			throw new RuntimeException("토큰을 가져오지 못했습니다.");
		}
	}

	/**
	 * Get Access Token , Refresh Token , Token Type , Expires In , Refresh Expires In , Not Before Policy , Session State , Scope
	 * @param keyCloakConfig
	 * @return
	 */
	public TokenResponse getAcessToken(KeyCloakConfig keyCloakConfig) {
		return setAcessTokenBuilder(keyCloakConfig);
	}

	// public UserResponse getUserInfo(KeyCloakConfig keyCloakConfig) {
	// 	AccessTokenResponse accessTokenResponse = keyCloakConfig.keycloak().tokenManager().getAccessToken();
	//
	// 	if (accessTokenResponse != null) {
	// 		HttpHeaders headers = new HttpHeaders();
	// 		RestTemplate restTemplate = new RestTemplateBuilder().build();
	//
	// 		headers.set("Authorization", "Bearer " + accessTokenResponse.getToken());
	// 		headers.setContentType(MediaType.APPLICATION_JSON);
	//
	// 		String userInfoUrl = keyCloakConfig.keycloak() +
	// 			"/realms/sinor/protocol/openid-connect/userinfo";
	// 		try {
	// 			return restTemplate.postForObject(userInfoUrl, headers, UserResponse.class);
	// 		} catch (HttpClientErrorException | HttpServerErrorException e) {
	// 			e.printStackTrace();
	// 			throw new RuntimeException("Failed to retrieve user information: " + e.getMessage());
	// 		}
	// 	} else {
	// 		throw new RuntimeException("Failed to retrieve user information: Access token is null.");
	// 	}
	// }

}
