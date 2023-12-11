package com.sinor.cache.user.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserCacheResponse {
	/**
	 * @param id
	 * @param createAt    생성시간
	 * @param ttl    설정 만료 시간 (Metadata value)
	 * @param url    상위 URL
	 * @param response    해당 캐시에 대한 응답
	 */
	private LocalDateTime createAt;
	private Long ttl;
	private String url;
	@JsonSerialize
	private String response;
}
