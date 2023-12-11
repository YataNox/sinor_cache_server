package com.sinor.cache.main.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCacheResponse {
	@JsonSerialize
	private String response;
}
