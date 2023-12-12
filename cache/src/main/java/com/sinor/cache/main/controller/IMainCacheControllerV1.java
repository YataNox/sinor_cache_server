package com.sinor.cache.main.controller;

import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.main.model.MainCacheRequset;
import org.springframework.util.MultiValueMap;

// https://www.baeldung.com/jackson-mapping-dynamic-object#using-jsonanysetter
// 어떤 형태로 요청이 들어오든 json 타입이라는 가정만 있으면 모두 Map<String, Object> 형식으로 저장 가능하다고 생각
// 기술적인 부분은 시간이 없어서 검증을 못했고, 일단 모든 Dto를 구분하는 기존 방식으로 작성

public interface IMainCacheControllerV1 {
	/**
	 * 데이터 조회 및 캐시 조회
	 *
	 * @param path        요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 */
	BaseResponse<?> getDataReadCache(String path, MultiValueMap<String, String> queryString);

	/**
	 * 데이터 조회 또는 생성 및 캐시 조회
	 *
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @param body 요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 */
	BaseResponse<?> postDataReadCache(String path, MultiValueMap<String, String> queryString, MainCacheRequset body);

	/**
	 * 데이터 삭제 및 캐시 갱신
	 *
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 */
	BaseResponse<?> deleteDataRefreshCache(String path, MultiValueMap<String, String> queryString);

	/**
	 * 데이터 수정 및 캐시 갱신
	 * @apiNote <a href="https://www.baeldung.com/spring-request-response-body#@requestbody">reference</a>
	 * @param path 요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @param body 요청에 전달된 RequestBody 내용에 매핑된 RequestBodyDto 객체
	 */
	BaseResponse<?> updateDataRefreshCache(String path, MultiValueMap<String, String> queryString, MainCacheRequset body);
}
