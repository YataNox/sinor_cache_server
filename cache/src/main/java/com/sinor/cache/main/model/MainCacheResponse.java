package com.sinor.cache.main.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainCacheResponse<T> {
	@JsonSerialize
	private T response;

	public static <T> MainCacheResponse<Object> from(T response){
		return MainCacheResponse.builder()
			.response(response)
			.build();
	}
}
