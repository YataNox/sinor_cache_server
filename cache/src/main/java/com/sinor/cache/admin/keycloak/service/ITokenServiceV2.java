package com.sinor.cache.admin.keycloak.service;

import com.sinor.cache.admin.keycloak.model.TokenResponse;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.config.KeyCloakConfig;

public interface ITokenServiceV2 {

	public TokenResponse setAcessTokenBuilder(KeyCloakConfig keyCloakConfig) throws CustomException;

	/**
	 * Get Access Token
	 * @param keyCloakConfig
	 * @return
	 */
	public TokenResponse getAcessToken(KeyCloakConfig keyCloakConfig) throws CustomException;

	// public UserResponse getUserInfo(KeyCloakConfig keyCloakConfig) throws CustomException;
}
