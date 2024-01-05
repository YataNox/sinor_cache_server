package com.sinor.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.auth.dto.AuthDto;
import com.sinor.auth.service.AuthService;
import com.sinor.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthApiController {
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

	private final AuthService authService;
	private final UserService userService;
	private final BCryptPasswordEncoder encoder;

	private final long COOKIE_EXPIRATION = 7776000; // 90일

	// 회원가입
	@PostMapping("/v1/signup")
	public ResponseEntity<Void> signup(@RequestBody @Valid AuthDto.SignupDto signupDto) {
		String encodedPassword = encoder.encode(signupDto.getPassword());
		// AuthDto.SignupDto newSignupDto = AuthDto.SignupDto.encodePassword(signupDto, encodedPassword);
		userService.registerUser(signupDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 로그인 -> 토큰 발급
	@PostMapping("/v1/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthDto.LoginDto loginDto) {
		// User 등록 및 Refresh Token 저장
		AuthDto.TokenDto tokenDto = authService.login(loginDto);

		// RT 저장
		HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
			.maxAge(COOKIE_EXPIRATION)
			.httpOnly(true)
			.secure(true)
			.build();
		Map<String,String> tokens = new HashMap<>();
		tokens.put("accessToken", tokenDto.getAccessToken());
		tokens.put("refreshToken", tokenDto.getRefreshToken());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, httpCookie.toString())
			// AT 저장
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
			.body(tokens);
			// .build();
	}

	@PostMapping("/v1/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
		if (!authService.validate(requestAccessToken)) {
			return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
		}
	}

	// 토큰 재발급
	@PostMapping("/v1/refresh")
	public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
		@RequestHeader("Authorization") String requestAccessToken) {
		AuthDto.TokenDto reissuedTokenDto = authService.reissue(requestAccessToken, requestRefreshToken);
		System.out.println("reissuedTokenDto : " + reissuedTokenDto);
		if (reissuedTokenDto != null) { // 토큰 재발급 성공
			System.out.println("토큰 재발급 성공");
			// RT 저장
			ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
				.maxAge(COOKIE_EXPIRATION)
				.httpOnly(true)
				.secure(true)
				.build();
			Map<String,String> tokens = new HashMap<>();
			tokens.put("accessToken", reissuedTokenDto.getAccessToken());
			tokens.put("refreshToken", reissuedTokenDto.getRefreshToken());
			return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				// AT 저장
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
				.body(tokens);
				// .build();
		} else { // Refresh Token 탈취 가능성
			System.out.println("재로그인 하세요");
			// Cookie 삭제 후 재로그인 유도
			ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
				.maxAge(0)
				.path("/")
				.build();
			return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.build();
		}
	}

	// 로그아웃
	@PostMapping("/v1/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
		authService.logout(requestAccessToken);
		ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
			.maxAge(0)
			.path("/")
			.build();

		return ResponseEntity
			.status(HttpStatus.OK)
			.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
			.build();
	}
}
