package com.sinor.cache.stroage.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CacheRes {
	// 캐시를 생성하거나, Admin측에서 조회하기 위한 Response
	// 유저에게는 하위에 들어갈 response만 반환
	private LocalDateTime createAt;
	private Long ttl;
	private String url;
	@JsonSerialize
	private String response;
}
