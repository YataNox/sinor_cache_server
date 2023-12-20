package com.sinor.cache.admin.keycloak.model;

import org.keycloak.representations.AccessTokenResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse extends AccessTokenResponse {
	/**
	 * 토큰 상세 정보
	 *
	 */
	private String access_token;
	private long expires_in;
	private long refresh_expires_in;
	private String refresh_token;
	private String token_type;
	private long not_before_policy;
	private String session_state;
	private String scope;
}
