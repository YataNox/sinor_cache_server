package com.sinor.cache.main.service;

import static com.sinor.cache.common.ResponseStatus.*;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.MetadataService;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MainCacheService implements IMainCacheServiceV1 {

	private final WebClient webClient;
	private final MetadataService metadataService;
	private final JsonToStringConverter jsonToStringConverter;
	private final RedisUtils redisUtils;
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * Main 서버에 요청을 보내는 메서드
	 *
	 * @param path        요청 path
	 * @param queryString 요청 queryString
	 */
	public String getMainPathData(String path, MultiValueMap<String, String> queryString) throws
		CustomException {

		//테스트 Main uri
		try {
			ResponseEntity<String> response = webClient.get()
				.uri(uriBuilder(path, queryString).build().toUri())
				.retrieve()
				.toEntity(String.class)
				.log()
				.block();

			return response.toString();

		} catch (WebClientResponseException e) {
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
	public ResponseEntity<String> postMainPathData(String path, MultiValueMap<String, String> queryString,
		Map<String, String> body) {

		try {
			return webClient.post()
				.uri(uriBuilder(path, queryString).build().toUri())
				.bodyValue(body)
				.retrieve()
				.toEntity(String.class)
				.log()
				.block();
		} catch (WebClientResponseException e) {
			throw new CustomException(ResponseStatus.DISPLAY_NOT_FOUND);
		}
	}

	/**
	 * Main 서버에 요청을 보내는 메서드
	 *
	 * @param path        요청 path
	 * @param queryString 요청 queryString
	 */
	public ResponseEntity<String> deleteMainPathData(String path, MultiValueMap<String, String> queryString) {

		try {
			return webClient.delete()
				.uri(uriBuilder(path, queryString).build().toUri())
				//.exchangeToMono(response -> response.bodyToMono(String.class))
				.retrieve()
				.toEntity(String.class)
				.log()
				.block();
		} catch (WebClientResponseException e) {
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
	public ResponseEntity<String> updateMainPathData(String path, MultiValueMap<String, String> queryString,
		Map<String, String> body) {
		try {
			return webClient.put()
				.uri(uriBuilder(path, queryString).build().toUri())
				.bodyValue(body)
				//.exchangeToMono(response -> response.bodyToMono(String.class))
				.retrieve()
				.toEntity(String.class)
				.log()
				.block();
		} catch (WebClientResponseException e) {
			throw new CustomException(ResponseStatus.DISPLAY_NOT_FOUND);
		}
	}

	/**
	 * 캐시에 데이터가 있는지 확인하고 없으면 데이터를 조회해서 있으면 데이터를 조회해서 반환해주는 메소드
	 * opsForValue() - Strings 를 쉽게 Serialize / Deserialize 해주는 Interface
	 *
	 * @param path 특정 path에 캐시가 있나 확인하기 위한 파라미터
	 * @return 값이 있다면 value, 없다면 null
	 */
	public String getDataInCache(String path, MultiValueMap<String, String> queryParams) throws
		CustomException {
		String uri = uriBuilder(path, queryParams).build().toUriString();

		System.out.println(uri);

		if (!redisUtils.isExist(uri))
			return postInCache(path, queryParams);

		try {
			System.out.println("역직렬화 시작");
			ApiGetResponse cachedData = objectMapper.readValue(redisTemplate.opsForValue().get(uri),
				ApiGetResponse.class);
			System.out.println("역직렬화 성공");
			return cachedData.getResponse();

		} catch (JsonProcessingException e) {
			throw new CustomException(DESERIALIZATION_ERROR);
		}
	}

	/**
	 * 캐시에 데이터가 없으면 메인 데이터를 조회해서 캐시에 저장하고 반환해주는 메소드
	 *
	 * @param path        검색할 캐시의 Path
	 * @param queryParams 각 캐시의 구별을 위한 QueryString
	 */
	public String postInCache(String path, MultiValueMap<String, String> queryParams) throws
		CustomException {

		//mainServer에서 받은 response
		String data = getMainPathData(path, queryParams);

		System.out.println(data);

		MetadataGetResponse metadata = metadataService.findOrCreateMetadataById(path);
		ApiGetResponse apiGetResponse = ApiGetResponse.from(metadata, data);
		String response = jsonToStringConverter.objectToJson(apiGetResponse);

		redisUtils.setRedisData(uriBuilder(path, queryParams).build().toUriString(), response, apiGetResponse.getTtl());

		System.out.println("value : " + response);

		System.out.println("createAt : " + apiGetResponse.getCreateAt());
		System.out.println("ttl : " + apiGetResponse.getTtl());
		System.out.println("url : " + apiGetResponse.getUrl());
		System.out.println("response : " + apiGetResponse.getResponse());

		return apiGetResponse.getResponse();
	}

	/**
	 * uri를 생성해주는 메소드
	 *
	 * @param path        검색할 캐시의 Path
	 * @param queryParams 각 캐시의 구별을 위한 QueryString
	 */
	public UriComponentsBuilder uriBuilder(String path, MultiValueMap<String, String> queryParams) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if (queryParams != null)
			builder.queryParams(queryParams);

		return builder;
	}

}
