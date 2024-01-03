package com.sinor.auth.OAuth2.model;

import lombok.Builder;
import lombok.Getter;

public class RefreshTokenDto {
	@Getter
	@Builder
	public static class Req {
		private String username;
		private String refreshToken;
	}

}
