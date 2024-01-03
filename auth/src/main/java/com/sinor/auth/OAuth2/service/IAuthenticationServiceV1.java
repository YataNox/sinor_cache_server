package com.sinor.auth.OAuth2.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.web.bind.annotation.RequestBody;

import com.sinor.auth.OAuth2.model.AuthenticationDto;
import com.sinor.auth.OAuth2.model.RefreshTokenDto;

public interface IAuthenticationServiceV1 {
	/**
	 * @apiNote 인증서버에서 발급한 엑세스톼근을 이용하여 인증을 처리한다.
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	AuthenticationDto.Res setAccessToken(@RequestBody AuthenticationDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

	/**
	 * @apiNote 인증서버에서 발급한 리프레시토큰을 이용하여 인증을 처리한다.
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	AuthenticationDto.Res setRefreshToken(@RequestBody RefreshTokenDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

}
