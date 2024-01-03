package com.sinor.cache.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {
	@Getter
	@AllArgsConstructor
	public static class Req {
		private String username;
		private String password;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Res {
		private String accessToken;
		private String refreshToken;
	}
}
