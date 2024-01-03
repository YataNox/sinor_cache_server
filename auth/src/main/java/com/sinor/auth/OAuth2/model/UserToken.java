package com.sinor.auth.OAuth2.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserToken {
	private final String subject;
	private final String username;
	private final Date expiration;
	private final String[] roles;
	private final String refreshToken;

	public boolean isExpired() {
		return new Date().getTime() > expiration.getTime();
	}
}