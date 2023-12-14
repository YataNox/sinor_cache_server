package com.sinor.cache.main.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.MetadataService;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class MainCacheService implements IMainCacheServiceV1 {
	private WebClient webClient;
	private final RedisUtils redisUtils;
	private final MetadataService metadataService;
	private final JsonToStringConverter jsonToStringConverter;

	/**
	 * Main 서버에 요청을 보내는 메서드
	 * @param path 요청 path
	 * @param queryString 요청 queryString
	 */
	public String getMainPathData(String path, MultiValueMap<String, String> queryString) {

		//테스트 Main uri
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if(queryString != null) builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());

		String mainResponse = webClient.get()
				.uri(builder.build().toUri())
				.retrieve()
				.bodyToMono(String.class) // 메인 서버에서 오는 요청을 String 으로 받는다.
				// main 서버는 모든 데이터에 대해 ok, data 형태로 넘어온다. 이를 받을 Response 객체를 활용할 수 없을까?
				.log()
				.block();
		System.out.println(mainResponse);
		// 여기서 그냥 반환을 MainServerResponse 로 하는 것
		// 캐시 서버에서 Main 의 데이터를 활용할 것도 아닌데 String 에서 꼭 변환을 해야할까?
		return mainResponse;
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 * @param path 요청 path
	 * @param queryString 요청 queryString
	 * @param body Requestbody
	 */
	public String postMainPathData(String path, MultiValueMap<String, String> queryString, Map<String, String> body) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if(queryString != null) builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());

		return webClient.post()
				.uri(builder.build().toUri())
				.bodyValue(body)
				.retrieve()
				.bodyToMono(String.class)
				.log()
				.block();
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 * @param path 요청 path
	 * @param queryString 요청 queryString
	 */
	public String deleteMainPathData(String path, MultiValueMap<String, String> queryString) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if(queryString != null) builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());

		return webClient.delete()
				.uri(builder.build().toUri())
				.retrieve()
				.bodyToMono(String.class)
				.log()
				.block();
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 * @param path 요청 path
	 * @param queryString 요청 queryString
	 * @param body Requestbody
	 */
	public String updateMainPathData(String path, MultiValueMap<String, String> queryString, Map<String, String> body) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if(queryString != null) builder.queryParams(queryString);
		// uri 확인
		System.out.println(builder.toUriString());

		return webClient.put()
				.uri(builder.build().toUri())
				.bodyValue(body)
				.retrieve()
				.bodyToMono(String.class)
				.log()
				.block();
	}


	/**
	 * 캐시에 데이터가 있는지 확인하고 없으면 데이터를 조회해서 있으면 데이터를 조회해서 반환해주는 메소드
	 * opsForValue() - Strings 를 쉽게 Serialize / Deserialize 해주는 Interface
	 * @param path 특정 path에 캐시가 있나 확인하기 위한 파라미터
	 * @return 값이 있다면 value, 없다면 null
	 */
	public String getDataInCache(String path) {
		if(!redisUtils.isExist(path))
			return null;

		ApiGetResponse cachedData = jsonToStringConverter.jsonToString(redisUtils.getRedisData(path), ApiGetResponse.class);
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
	public String postInCache(String path, MultiValueMap<String, String> queryString) throws CustomException{

		String data = getMainPathData(path, queryString);

		System.out.println(data);

		MetadataGetResponse metadata = metadataService.findOrCreateMetadataById(path);

		ApiGetResponse apiGetResponse = ApiGetResponse.from(metadata, data);

		data = jsonToStringConverter.objectToJson(apiGetResponse);
		System.out.println(data);
		redisUtils.setRedisData(path, data, apiGetResponse.getTtl());

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
