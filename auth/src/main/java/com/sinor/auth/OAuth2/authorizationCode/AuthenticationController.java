package com.sinor.auth.OAuth2.authorizationCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
	private final UserDetailsService userDetailsService;
	private final JwtTokenService jwtTokenService;
	private final PasswordEncoder passwordEncoder;

	private final long EXPIRATION_TIME = 864_000_000; // 10일 (단위: 밀리초)

	@PostMapping("/api/v1/authentication")
	public AuthenticationDto.Res authentication(@RequestBody AuthenticationDto.Req request) {
		String token = Optional.ofNullable(userDetailsService.loadUserByUsername(request.getUsername()))
			.filter(it -> passwordEncoder.matches(request.getPassword(), it.getPassword()))
			.map(it -> {
				try {
					return jwtTokenService.createToken(UserToken.builder()
						.username(request.getUsername())
						.roles(it.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority)
							.toArray(String[]::new))
						.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.build());
				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				} catch (InvalidKeySpecException e) {
					throw new RuntimeException(e);
				}
			})
			.orElseThrow(() -> new IllegalStateException("user not found. username: " + request.getUsername()));
		return AuthenticationDto.Res.builder()
			.token(token)
			.build();
	}
}