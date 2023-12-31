package com.sinor.auth.OAuth2.authorizationCode;

import lombok.Builder;
import lombok.Getter;

public class RefreshTokenDto {
	@Getter
	@Builder
	public static class Req {
		
		private String refreshToken;

	}
}
