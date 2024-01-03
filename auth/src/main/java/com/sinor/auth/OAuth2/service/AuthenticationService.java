package com.sinor.auth.OAuth2.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sinor.auth.OAuth2.model.AuthenticationDto;
import com.sinor.auth.OAuth2.model.RefreshTokenDto;
import com.sinor.auth.OAuth2.model.UserToken;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationServiceV1 {
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtTokenService jwtTokenService;
	private final long EXPIRATION_TIME = 864_000_000;

	@Override
	public AuthenticationDto.Res setAccessToken(@RequestBody AuthenticationDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		authenticateUser(request.getUsername(), request.getPassword());
		UserToken userToken = buildUserToken(request.getUsername());
		String accessToken = generateAccessToken(userToken);
		String refreshToken = generateRefreshToken(request.getUsername());

		return AuthenticationDto.Res.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public AuthenticationDto.Res setRefreshToken(RefreshTokenDto.Req request) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		UserToken userToken = extractUserToken(request.getRefreshToken());
		String newAccessToken = generateAccessToken(userToken);

		return AuthenticationDto.Res.builder()
			.accessToken(newAccessToken)
			.refreshToken(request.getRefreshToken())
			.build();
	}

	private UserToken buildUserToken(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return UserToken.builder()
			.username(username)
			.roles(userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toArray(String[]::new))
			.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.build();
	}

	private UserToken extractUserToken(String refreshToken) {
		try {
			return jwtTokenService.parseRefreshToken(refreshToken);
		} catch (JwtException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AuthenticationServiceException("Invalid refresh token.", e);
		}
	}

	private String generateAccessToken(UserToken userToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return jwtTokenService.createAccessToken(userToken);
	}

	private String generateRefreshToken(String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return jwtTokenService.createRefreshToken(RefreshTokenDto.Req.builder()
			.username(username)
			.build());
	}

	public void authenticateUser(String username, String password) {
		Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(username, password);
		authenticationManager.authenticate(authenticationRequest);

	}

}
