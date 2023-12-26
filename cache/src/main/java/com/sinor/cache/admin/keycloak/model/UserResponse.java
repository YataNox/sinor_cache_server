package com.sinor.cache.admin.keycloak.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	/**
	 * userInfo 상세정보
	 */
	private String sub;
	private boolean email_verified;
	private String name;
	private String preferred_username;
	private String given_name;
	private String locale;
	private String family_name;
	private String email;

	// {
	// 	"sub": "0e3ebe0a-370e-48bf-b977-728696031d75",
	// 	"email_verified": false,
	// 	"name": "si nor",
	// 	"preferred_username": "sinoradmin",
	// 	"given_name": "si",
	// 	"locale": "en",
	// 	"family_name": "nor",
	// 	"email": "sinor@sinor.co.kr"
	// }
}
