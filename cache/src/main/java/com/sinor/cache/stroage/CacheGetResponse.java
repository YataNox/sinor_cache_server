package com.sinor.cache.stroage;

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
public class CacheGetResponse {
	// 캐시를 생성하거나, Admin측에서 조회하기 위한 Response
	// 유저에게는 하위에 들어갈 response만 반환
	private LocalDateTime createAt; // 생성시간
	private Long ttl; // 설정 만료 시간 (Metadata value)
	private String url; // 상위 URL
	@JsonSerialize
	private String response; // 해당 캐시에 대한 응답
}
