package com.sinor.cache.user.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

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
			.uri("http://mainHost:8080/{path}", path)
			.retrieve()
			.bodyToMono(UserCacheResponse.class)
			.log()
			.block();
	}
	
	/**
	 * 캐시에 데이터가 있는지 확인하고 없으면 데이터를 조회해서 있으면 데이터를 조회해서 반환해주는 메소드
	 * opsForValue() - Strings를 쉽게 Serialize / Deserialize 해주는 Interface
	 * @parampath
	 * @paramqueryString
	 * @return
	 */
	public String getDataInCache(String path) {
		String cachedData = redisTemplate.opsForValue().get(path);
		System.out.println("cachedData: " + cachedData + "값입니다.");
		return cachedData;
	}

	/**
	 * 캐시에 데이터가 없으면 메인 데이터를 조회해서 캐시에 저장하고 반환해주는 메소드
	 * @param path
	 * @param queryString
	 * @return
	 */
	public UserCacheResponse postInCache(String path, String queryString) {
		UserCacheResponse userCacheResponse = getMainPathData(path, queryString);
		System.out.println("userCacheResponse = " + userCacheResponse);
		redisTemplate.opsForValue().set(path, String.valueOf(userCacheResponse), Duration.ofMinutes(10));
		System.out.println(redisTemplate.opsForValue().get(path));
		return userCacheResponse;
	}

	// 1. RequestBody에 다음과 같은 형식으로 전달하면 캐시에 저장해주는 API
	// application/json
	// {
	//  "key": /main/expression,
	//  "value": { "url": "http://localhost:8080/main/expression", "createAt": "2021-08-31T15:00:00", "ttl": 600 }
	// }
	// public UserCacheResponse postCache(String key, Object value) {
	// 	try {
	// 		String jsonValue = objectMapper.writeValueAsString(value);
	// 		redisTemplate.opsForValue().set(key, jsonValue);
	// 		System.out.println("redisTemplate.opsForValue().get(key) = " + redisTemplate.opsForValue().get(key));
	// 		return objectMapper.readValue(jsonValue, UserCacheResponse.class);
	// 	} catch (Exception e) {
	// 		e.fillInStackTrace();
	// 		return null;
	// 	}
	// }
}
