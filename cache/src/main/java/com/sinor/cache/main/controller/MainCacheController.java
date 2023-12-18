package com.sinor.cache.main.controller;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sinor.cache.common.CustomResponse;
import com.sinor.cache.main.model.MainCacheRequest;
import com.sinor.cache.main.service.MainCacheService;

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
	public ResponseEntity<JsonNode> getDataReadCache(String path, MultiValueMap<String, String> queryParams) {
		CustomResponse pathCache = mainCacheService.getDataInCache(path, queryParams);
		if (pathCache == null) {
			pathCache = mainCacheService.postInCache(path, queryParams);
		}
		return ResponseEntity.status(pathCache.getStatusCodeValue()).headers(
			(Consumer<HttpHeaders>)pathCache.getHeaders()).body(pathCache.getBody());
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
	public ResponseEntity<String> postDataReadCache(String path, MultiValueMap<String, String> queryParams, MainCacheRequest body) {

		return mainCacheService.postMainPathData(path, queryParams, body.getRequestBody());
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
		return mainCacheService.deleteMainPathData(path, queryParams);
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
	public ResponseEntity<String> updateDataRefreshCache(String path, MultiValueMap<String, String> queryParams, MainCacheRequest body) {
		return mainCacheService.updateMainPathData(path, queryParams, body.getRequestBody());
	}
}