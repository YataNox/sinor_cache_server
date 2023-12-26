package com.sinor.cache.admin.keycloak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.admin.keycloak.model.TokenResponse;
import com.sinor.cache.admin.keycloak.service.TokenService;
import com.sinor.cache.config.KeyCloakConfig;

@RestController
public class TokenController {
	private final KeyCloakConfig keyCloakConfig;
	private final TokenService tokenService;

	public TokenController(KeyCloakConfig keyCloakConfig, TokenService tokenService) {
		this.keyCloakConfig = keyCloakConfig;
		this.tokenService = tokenService;
	}

	/**
	 * 시큐리티 테스트
	 * @return
	 */
	@GetMapping("/admin/test/1")
	public String hi() {
		return "admin hi";
	}

	@GetMapping("/user/test/1")
	public String bye() {
		return "user bye";
	}

	/**
	 * Get Access Token
	 * @param keyCloakConfig
	 * @return
	 */
	@PostMapping("/realms/sinor/protocol/openid-connect/token")
	public TokenResponse getAcessToken(KeyCloakConfig keyCloakConfig) {
		return tokenService.getAcessToken(keyCloakConfig);
	}

	// @PostMapping("/admin/userinfo")
	// public UserResponse getUserInfo(KeyCloakConfig keyCloakConfig) {
	// 	return tokenService.getUserInfo(keyCloakConfig);
	// }

}
