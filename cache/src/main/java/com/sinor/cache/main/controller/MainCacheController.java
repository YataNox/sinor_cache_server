package com.sinor.cache.main.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.common.AdminSuccessResponse;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;
import com.sinor.cache.main.model.MainCacheResponse;
import com.sinor.cache.main.service.MainCacheService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class MainCacheController implements IMainCacheControllerV1<MainCacheResponse, MainCacheResponse> {
	private final MainCacheService mainCacheService;

	/**
	 * 데이터 조회 및 캐시 조회
	 *
	 * @param path        요청에 전달된 path
	 * @param queryParams 요청에 전달된 queryString
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	@Override
	public AdminSuccessResponse<?> getDataReadCache(String path,
		Map<String, String> queryParams) {
		try {
			String pathCache = mainCacheService.getDataInCache(path);
			if (pathCache == null) {
				return new AdminSuccessResponse<>(ResponseStatus.SUCCESS, mainCacheService.postInCache(path, queryParams.get(0)));
			}

			return new AdminSuccessResponse<>(ResponseStatus.SUCCESS, pathCache);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
			return new AdminSuccessResponse<>(e.getStatus());
		}
	}

	/**
	 * 데이터 조회 또는 생성 및 캐시 조회
	 *
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @param body 요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 */
	@Override
	public MainCacheResponse postDataReadCache(String path, String queryString, MainCacheResponse body) {

		return null;
	}

	/**
	 * 데이터 삭제 및 캐시 갱신
	 *
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @param body 요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 */
	@Override
	public MainCacheResponse deleteDataRefreshCache(String path, String queryString, MainCacheResponse body) {
		return null;
	}

	/**
	 * 데이터 수정 및 캐시 갱신
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @param body 요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 */
	@Override
	public MainCacheResponse updateDataRefreshCache(String path, String queryString, MainCacheResponse body) {
		return null;
	}
}