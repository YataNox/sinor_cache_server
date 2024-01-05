package com.sinor.cache.main.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.main.model.MainCacheRequest;
import com.sinor.cache.main.model.MainCacheResponse;
import com.sinor.cache.main.service.MainCacheService;
import com.sinor.cache.utils.URIUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainCacheController implements IMainCacheControllerV1 {

	private final MainCacheService mainCacheService;

	public MainCacheController(MainCacheService mainCacheService) {
		this.mainCacheService = mainCacheService;
	}

	/**
	 * 데이터 조회 및 캐시 조회
	 *
	 * @param path        요청에 전달된 path
	 * @param queryParams 요청에 전달된 queryString
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	@Override
	public ResponseEntity<?> getDataReadCache(String path, MultiValueMap<String, String> queryParams,
		MultiValueMap<String, String> headers) {

		MultiValueMap<String, String> encodedQueryParams = URIUtils.encodingUrl(queryParams);

		MainCacheResponse pathCache = mainCacheService.getDataInCache(path, encodedQueryParams, headers);

		// 메인 요청 및 캐시 생성
		if (pathCache == null)
			pathCache = mainCacheService.postInCache(path, encodedQueryParams, headers);

		log.info("check body : {}", pathCache.getBody());

		// 헤더 재조립
		HttpHeaders header = new HttpHeaders();
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.setAll(pathCache.getHeaders());
		header.addAll(multiValueMap);

		return ResponseEntity.status(pathCache.getStatusCodeValue()).headers(header).body(pathCache.getBody());
	}

	/**
	 * 데이터 조회 또는 생성 및 캐시 조회
	 *
	 * @param path        요청에 전달된 path
	 * @param queryParams 요청에 전달된 queryString
	 * @param body        요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	@Override
	public ResponseEntity<String> postDataReadCache(String path, MultiValueMap<String, String> queryParams,
		MainCacheRequest body) {

		return mainCacheService.postMainPathData(path, URIUtils.encodingUrl(queryParams),
			body.getRequestBody());
	}

	/**
	 * 데이터 삭제 및 캐시 갱신
	 *
	 * @param path        요청에 전달된 path
	 * @param queryParams 요청에 전달된 queryString
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	@Override
	public ResponseEntity<String> deleteDataRefreshCache(String path, MultiValueMap<String, String> queryParams) {
		return mainCacheService.deleteMainPathData(path, URIUtils.encodingUrl(queryParams));
	}

	/**
	 * 데이터 수정 및 캐시 갱신
	 *
	 * @param path        요청에 전달된 path
	 * @param queryParams 요청에 전달된 queryString
	 * @param body        요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	@Override
	public ResponseEntity<String> updateDataRefreshCache(String path, MultiValueMap<String, String> queryParams,
		MainCacheRequest body) {
		return mainCacheService.updateMainPathData(path, URIUtils.encodingUrl(queryParams),
			body.getRequestBody());
	}
}