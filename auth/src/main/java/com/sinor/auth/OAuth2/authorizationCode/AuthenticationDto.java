package com.sinor.auth.OAuth2.authorizationCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AuthenticationDto {
	@Getter
	@AllArgsConstructor
	public static class Req {
		private final String username;
		private final String password;
	}

	@Getter
	@Builder
	public static class Res {
		private final String token;
		private final String refreshToken;
	}
}