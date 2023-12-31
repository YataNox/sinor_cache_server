package com.sinor.auth.OAuth2.authorizationCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
	private final UserDetailsService userDetailsService;
	private final JwtTokenService jwtTokenService;

	private final long EXPIRATION_TIME = 864_000_000;

	@PostMapping("/api/v1/authentication")
	public AuthenticationDto.Res authentication(@RequestBody AuthenticationDto.Req request) {
		UserToken userToken = UserToken.builder()
			.username(request.getUsername())
			.roles(userDetailsService.loadUserByUsername(request.getUsername()).getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toArray(String[]::new))
			.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.build();
		String accessToken = null;
		String refreshToken = null;
		try {
			accessToken = jwtTokenService.createAccessToken(userToken);
			refreshToken = jwtTokenService.createRefreshToken();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}

		if (accessToken == null || refreshToken == null) {
			throw new IllegalStateException("user not found. username: " + request.getUsername());
		}

		return AuthenticationDto.Res.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@PostMapping("/api/v1/refresh")
	public AuthenticationDto.Res refreshToken(@RequestBody RefreshTokenDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		UserToken userToken = jwtTokenService.parseRefreshToken(request.getRefreshToken());
		String newAccessToken = null;
		try {
			newAccessToken = jwtTokenService.createAccessToken(userToken);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}

		if (newAccessToken == null) {
			throw new IllegalStateException("Unable to refresh token for user: " + userToken.getUsername());
		}

		return AuthenticationDto.Res.builder()
			.accessToken(newAccessToken)
			.refreshToken(request.getRefreshToken())
			.build();
	}

}