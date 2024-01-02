package com.sinor.cache.admin.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthenticationDto {
	@Getter
	@Builder
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
