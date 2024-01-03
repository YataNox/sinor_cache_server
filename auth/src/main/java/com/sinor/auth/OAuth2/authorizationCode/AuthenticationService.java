package com.sinor.auth.OAuth2.authorizationCode;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	@Override
		public AuthenticationDto.Res authentication(@RequestBody AuthenticationDto.Req request) {
		authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
	}

}