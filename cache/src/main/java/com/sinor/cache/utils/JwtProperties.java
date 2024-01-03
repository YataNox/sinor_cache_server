package com.sinor.cache.utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtProperties {
	private String authorization;
	private String username;
	private String password;
}
