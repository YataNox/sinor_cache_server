package com.sinor.auth.auth.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.security.core.Authentication;

import com.sinor.auth.auth.dto.AuthDto;

public interface IAuthServiceV1 {

	/**
	 * 로그인
	 * @param loginDto
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	AuthDto.TokenDto login(AuthDto.LoginDto loginDto) throws NoSuchAlgorithmException, InvalidKeySpecException;

	/**
	 * 토큰 검증
	 * @param requestAccessTokenInHeader
	 * @return
	 */
	boolean validate(String requestAccessTokenInHeader);

	/**
	 * 토큰 재발급
	 * @param requestAccessTokenInHeader
	 * @param requestRefreshToken
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	AuthDto.TokenDto reissue(String requestAccessTokenInHeader, String requestRefreshToken) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

	/**
	 * 토큰 생성
	 * @param provider
	 * @param email
	 * @param authorities
	 * @return
	 */
	AuthDto.TokenDto generateToken(String provider, String email, String authorities);

	/**
	 * 토큰 저장
	 * @param provider
	 * @param principal
	 * @param refreshToken
	 */
	void saveRefreshToken(String provider, String principal, String refreshToken);

	String getAuthorities(Authentication authentication);

	String getPrincipal(String requestAccessToken);

	/**
	 * 토큰 조회
	 * @param requestAccessTokenInHeader
	 * @return
	 */
	String resolveToken(String requestAccessTokenInHeader);

	/**
	 * 로그아웃
	 * @param requestAccessTokenInHeader
	 */
	void logout(String requestAccessTokenInHeader);

}
