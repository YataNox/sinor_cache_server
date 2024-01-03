package com.sinor.auth.OAuth2.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.web.bind.annotation.RestController;

import com.sinor.auth.OAuth2.model.AuthenticationDto;
import com.sinor.auth.OAuth2.model.RefreshTokenDto;
import com.sinor.auth.OAuth2.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements IAuthenticationControllerV1 {
	private final AuthenticationService AuthenticationService;

	public AuthenticationDto.Res setAccessToken(AuthenticationDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		return AuthenticationService.setAccessToken(request);
	}

	public AuthenticationDto.Res setRefreshToken(RefreshTokenDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		return AuthenticationService.setRefreshToken(request);
	}

}