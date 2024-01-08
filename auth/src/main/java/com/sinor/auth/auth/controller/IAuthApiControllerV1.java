package com.sinor.auth.auth.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sinor.auth.auth.dto.AuthDto;

import jakarta.validation.Valid;

public interface IAuthApiControllerV1 {
	/**
	 * 1. 회원가입
	 * 2. 로그인 -> 토큰 발급
	 * 3. 토큰 재발급
	 * 4. 로그아웃
	 *
	 * 회원가입은 localhost/auth/v1/signup
	 * Body Email,Password -> Json 형태로 전달
	 *
	 */
	// 회원가입
	@PostMapping("/v1/signup")
	ResponseEntity<Void> signup(@RequestBody @Valid AuthDto.SignupDto signupDto);

	/**
	 * 로그인
	 * @param loginDto
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	@PostMapping("/v1/login")
	ResponseEntity<?> login(@RequestBody @Valid AuthDto.LoginDto loginDto) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

	/**
	 * 토큰 재발급
	 * @param requestRefreshToken
	 * @param requestAccessToken
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	@PostMapping("/v1/refresh")
	ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
		@RequestHeader("Authorization") String requestAccessToken) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException;

	/**
	 * 토큰 검증
	 * @param requestAccessToken
	 * @return
	 */
	@PostMapping("/v1/validate")
	ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken);

	/**
	 * 로그아웃
	 * @param requestAccessToken
	 * @return
	 */
	@PostMapping("/v1/logout")
	ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken);
}
