package com.sinor.cache.main.controller;


import com.sinor.cache.main.model.MainCacheRequset;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.common.BaseResponseStatus;
import com.sinor.cache.main.service.MainCacheService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MainCacheController implements IMainCacheControllerV1 {

	private final MainCacheService mainCacheService;

	/**
	 * 데이터 조회 및 캐시 조회
	 *
	 * @param path        요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	@Override
	@GetMapping("/{path}")
	public BaseResponse<?> getDataReadCache(@PathVariable String path,
		@RequestParam(required = false) MultiValueMap<String, String> queryString) {
		try {
			String pathCache = mainCacheService.getDataInCache(path);
			if (pathCache == null) {
				return new BaseResponse<>(BaseResponseStatus.SUCCESS, mainCacheService.postInCache(path, queryString));
			}

			return new BaseResponse<>(BaseResponseStatus.SUCCESS, pathCache);
		} catch (BaseException e) {
			System.out.println(e.getMessage());
			return new BaseResponse<>(e.getStatus());
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
	@PostMapping("/{path}")
	public BaseResponse<?> postDataReadCache(String path, MultiValueMap<String, String> queryString, MainCacheRequset body) {

		return new BaseResponse<>(BaseResponseStatus.SUCCESS, mainCacheService.postMainPathData(path, queryString, body));
	}

	/**
	 * 데이터 삭제 및 캐시 갱신
	 *
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 */
	@Override
	@DeleteMapping("/{path}")
	public BaseResponse<?> deleteDataRefreshCache(String path, MultiValueMap<String, String> queryString) {

		return new BaseResponse<>(BaseResponseStatus.SUCCESS, mainCacheService.deleteMainPathData(path, queryString));
	}

	/**
	 * 데이터 수정 및 캐시 갱신
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @param body 요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 */
	@Override
	@PutMapping("/{path}")
	public BaseResponse<?> updateDataRefreshCache(String path, MultiValueMap<String, String> queryString, MainCacheRequset body) {

		return new BaseResponse<>(BaseResponseStatus.SUCCESS, mainCacheService.updateMainPathData(path, queryString, body));
	}
}