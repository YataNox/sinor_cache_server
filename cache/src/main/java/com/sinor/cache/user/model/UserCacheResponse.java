package com.sinor.cache.user.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCacheResponse {
	@JsonSerialize
	private String response;
}
