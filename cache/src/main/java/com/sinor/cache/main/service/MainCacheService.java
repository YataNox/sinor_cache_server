package com.sinor.cache.main.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.MetadataService;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;
import com.sinor.cache.main.model.MainCacheResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class MainCacheService implements IMainCacheServiceV1 {
	private WebClient webClient;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private final MetadataService metadataService;

	/**
	 * Main 서버에 요청을 보내는 메서드
	 * @param path 요청 path
	 * @param queryString 요청 queryString
	 */
	public String getMainPathData(String path, String queryString) throws JsonProcessingException {
		String mainResponse = webClient.get()
			.uri("http://mainHost:8080/{path}", path)
			.retrieve()
			.bodyToMono(String.class) // 메인 서버에서 오는 요청을 String으로 받는다.
			// main 서버는 모든 데이터에 대해 ok, data형태로 넘어온다. 이를 받을 Response 객체를 활용할 수 없을까?
			.log()
			.block();
		System.out.println(mainResponse);
		// 여기서 그냥 반환을 MainServerResponse로 하는 것
		// 캐시 서버에서 Main의 데이터를 활용할 것도 아닌데 String에서 꼭 변환을 해야할까?
		return mainResponse;
	}

	/**
	 * 캐시에 데이터가 있는지 확인하고 없으면 데이터를 조회해서 있으면 데이터를 조회해서 반환해주는 메소드
	 * opsForValue() - Strings를 쉽게 Serialize / Deserialize 해주는 Interface
	 * @param path
	 * @return
	 */
	public String getDataInCache(String path) {
		String cachedData = redisTemplate.opsForValue().get(path);
		System.out.println("cachedData: " + cachedData + "값입니다.");
		return cachedData;
	}

	/**
	 * 캐시에 데이터가 없으면 메인 데이터를 조회해서 캐시에 저장하고 반환해주는 메소드
	 * @param path 검색할 캐시의 Path
	 * @param queryString 각 캐시의 구별을 위한 QueryString
	 */
	public MainCacheResponse postInCache(String path, String queryString) throws CustomException {
		try {
			String response = getMainPathData(path, queryString);

			MainCacheResponse userCacheResponse = MainCacheResponse.builder()
				.response(response)
				.build();

			MetadataGetResponse metadata = metadataService.findOrCreateMetadataById(path);
			redisTemplate.opsForValue().set(path, objectMapper.writeValueAsString(userCacheResponse), metadata.getMetadataTtlSecond());

			return userCacheResponse;

		} catch (JsonProcessingException e) {
			throw new CustomException(ResponseStatus.JSON_PROCESSING_EXCEPTION);
		}
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
