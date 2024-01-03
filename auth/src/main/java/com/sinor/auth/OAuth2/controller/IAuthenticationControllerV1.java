package com.sinor.auth.OAuth2.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sinor.auth.OAuth2.model.AuthenticationDto;
import com.sinor.auth.OAuth2.model.RefreshTokenDto;

public interface IAuthenticationControllerV1 {
	/**
	 * @apiNote 인증서버에서 발급한 엑세스톼큰을 이용하여 인증을 처리한다.
	 * @param request
	 * @return
	 */
	@PostMapping("/api/v1/authentication")
	AuthenticationDto.Res setAccessToken(@RequestBody AuthenticationDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

	/**
	 * @apiNote 리프레시 토큰을 이용하여 엑세스 토큰을 재발급한다.
	 * @param request
	 * @return
	 */

	@PostMapping("/api/v1/refresh")
	AuthenticationDto.Res setRefreshToken(@RequestBody RefreshTokenDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

}