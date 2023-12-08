package com.sinor.cache.user.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinor.cache.user.model.UserCacheResponse;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserCacheService {
	private WebClient webClient;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	public UserCacheResponse getMainPathData(String path, String queryString) {
		return webClient.get()
			.uri("/main/{path}", path)
			.retrieve()
			.bodyToMono(UserCacheResponse.class)
			.log()
			.block();
	}

	// 메인 서버에서 데이터를 가져와 캐시에 저장하고 데이터를 반환하는 메서드
	public UserCacheResponse fetchDataAndStoreInCache(String path, String queryString) throws JsonProcessingException {
		String cachedData = redisTemplate.opsForValue().get(path);
		System.out.println("cachedData: " + cachedData + "값입니다.");
		if (cachedData == null) {
			System.out.println("getMainPathData 진입: " + "path : " + path + ", queryString: " + queryString);
			//문제 발생 근원지
			UserCacheResponse userCacheResponse = getMainPathData(path, queryString);
			redisTemplate.opsForValue().set(path, String.valueOf(userCacheResponse), Duration.ofMinutes(10));
			// 여기서 WebClient에서 가져온 데이터를 CacheResponse로 매핑하여 반환합니다.
			return mapToCacheResponse(userCacheResponse);
		}

		// 여기서는 캐시된 데이터를 CacheResponse로 매핑하여 반환합니다.
		System.out.println("notNull 영역 진입 cachedData: " + cachedData);
		System.out.println("cachedData: " + cachedData);
		return mapToCacheResponse(objectMapper.readValue(cachedData, UserCacheResponse.class));
	}

	// WebClient에서 가져온 데이터를 CacheResponse로 매핑하는 메서드
	private UserCacheResponse mapToCacheResponse(UserCacheResponse userCacheResponse) {
		UserCacheResponse cacheResponse = new UserCacheResponse();
		cacheResponse.setCreateAt(LocalDateTime.now()); // 현재 시간을 설정 (원하는 대로 변경 가능)
		cacheResponse.setTtl(600L); // TTL을 600초로 설정 (원하는 대로 변경 가능)
		cacheResponse.setUrl(userCacheResponse.getUrl()); // 원하는 URL로 설정 (원하는 대로 변경 가능)
		cacheResponse.setResponse(userCacheResponse.getResponse()); // 실제 응답 데이터를 설정
		System.out.println("cacheResponse: " + cacheResponse);
		return cacheResponse;
	}
}
