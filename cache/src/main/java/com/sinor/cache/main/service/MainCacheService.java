package com.sinor.cache.main.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.MetadataService;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class MainCacheService implements IMainCacheServiceV1 {
	private WebClient webClient;
	private final MetadataService metadataService;
	private final JsonToStringConverter jsonToStringConverter;
	private final RedisUtils redisUtils;

	/**
	 * Main 서버에 요청을 보내는 메서드
	 *
	 * @param path        요청 path
	 * @param queryString 요청 queryString
	 */
	public ResponseEntity<JsonNode> getMainPathData(String path, MultiValueMap<String, String> queryString) throws CustomException {

		//테스트 Main uri
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if (queryString != null)
			builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());
		try {
			//TODO toString()으로는 원하는 값이 안나온다면? => getStatusCode(), getHeaders(), getBody()로 직접 받자
			return webClient.get()
				.uri(builder.build().toUri())
				.retrieve()
				.toEntity(JsonNode.class)
				.log()
				.block();
		}catch (WebClientResponseException e){
			throw new CustomException(ResponseStatus.DISPLAY_NOT_FOUND);
		}
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 *
	 * @param path        요청 path
	 * @param queryString 요청 queryString
	 * @param body        Requestbody
	 */
	public ResponseEntity<JsonNode> postMainPathData(String path, MultiValueMap<String, String> queryString, Map<String, String> body) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if (queryString != null)
			builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());
		try{
			return webClient.post()
				.uri(builder.build().toUri())
				.bodyValue(body)
				.retrieve()
				.toEntity(JsonNode.class)
				.log()
				.block();
		}catch (WebClientResponseException e){
			throw new CustomException(ResponseStatus.DISPLAY_NOT_FOUND);
		}
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 *
	 * @param path        요청 path
	 * @param queryString 요청 queryString
	 */
	public ResponseEntity<JsonNode> deleteMainPathData(String path, MultiValueMap<String, String> queryString) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if (queryString != null)
			builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());
		try {
			return webClient.delete()
				.uri(builder.build().toUri())
				//.exchangeToMono(response -> response.bodyToMono(String.class))
				.retrieve()
				.toEntity(JsonNode.class)
				.log()
				.block();
		}catch (WebClientResponseException e){
			throw new CustomException(ResponseStatus.DISPLAY_NOT_FOUND);
		}
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 *
	 * @param path        요청 path
	 * @param queryString 요청 queryString
	 * @param body        Requestbody
	 */
	public ResponseEntity<JsonNode> updateMainPathData(String path, MultiValueMap<String, String> queryString, Map<String, String> body) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if (queryString != null)
			builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());
		try {
			return webClient.put()
				.uri(builder.build().toUri())
				.bodyValue(body)
				//.exchangeToMono(response -> response.bodyToMono(String.class))
				.retrieve()
				.toEntity(JsonNode.class)
				.log()
				.block();
		}catch (WebClientResponseException e){
			throw new CustomException(ResponseStatus.DISPLAY_NOT_FOUND);
		}
	}

	/**
	 * 캐시에 데이터가 있는지 확인하고 없으면 데이터를 조회해서 있으면 데이터를 조회해서 반환해주는 메소드
	 * opsForValue() - Strings 를 쉽게 Serialize / Deserialize 해주는 Interface
	 * @param path 특정 path에 캐시가 있나 확인하기 위한 파라미터
	 * @return 값이 있다면 value, 없다면 null
	 */
	public String getDataInCache(String path) {
		if (!redisUtils.isExist(path))
			return null;

		ApiGetResponse cachedData = jsonToStringConverter.jsonToString(redisUtils.getRedisData(path),
			ApiGetResponse.class);
		System.out.println("cachedData.response: " + cachedData.getResponse() + "값 입니다.");
		System.out.println("cachedData.url: " + cachedData.getUrl() + "값 입니다.");
		System.out.println("cachedData.ttl: " + cachedData.getTtl() + "값 입니다.");
		System.out.println("cachedData.createAt: " + cachedData.getCreateAt() + "값 입니다.");

		return cachedData.getResponse();
	}

	/**
	 * 캐시에 데이터가 없으면 메인 데이터를 조회해서 캐시에 저장하고 반환해주는 메소드
	 * @param path 검색할 캐시의 Path
	 * @param queryString 각 캐시의 구별을 위한 QueryString
	 */
	public String postInCache(String path, MultiValueMap<String, String> queryString) throws CustomException {

		ResponseEntity<JsonNode> data = getMainPathData(path, queryString);

		MetadataGetResponse metadata = metadataService.findOrCreateMetadataById(path);
		ApiGetResponse apiGetResponse = ApiGetResponse.from(metadata, data.toString());
		String response = jsonToStringConverter.objectToJson(apiGetResponse);
		redisUtils.setRedisData(path, response, apiGetResponse.getTtl());

		return apiGetResponse.getResponse();
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
