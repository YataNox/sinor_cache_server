package com.sinor.cache.admin.api.service;

import java.util.List;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.common.CustomException;

public interface IApiServiceV1 {

	/**
	 * 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	ApiGetResponse findCacheById(String key) throws CustomException;

	/**
	 * 패턴과 일치하는 캐시 조회
	 * @param pattern 조회할 캐시들의 공통 패턴
	 */
	List<ApiGetResponse> findCacheList(String pattern) throws CustomException;

	/**
	 * 전체 캐시 조회
	 */
	List<ApiGetResponse> findAllCache();

	/**
	 * 캐시 생성 및 덮어쓰기
	 * @param key 생성할 캐시의 Key
	 * @param value 생성할 캐시의 Value
	 * @param expiredTime 생성할 캐시의 만료시간
	 */
	ApiGetResponse saveOrUpdate(String key, String value, int expiredTime) throws CustomException;

	/**
	 * 캐시 삭제
	 * @param key 삭제할 캐시의 Key
	 */
	void deleteCacheById(String key) throws CustomException;

	/**
	 * 패턴과 일치하는 캐시 삭제
	 * @param pattern 삭제할 캐시들의 공통 패턴
	 */
	void deleteCacheList(String pattern) throws CustomException;
}
