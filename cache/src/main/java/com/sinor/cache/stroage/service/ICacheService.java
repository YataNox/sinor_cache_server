package com.sinor.cache.stroage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sinor.cache.stroage.response.CacheGetResponse;

@Service
public interface ICacheService {

	/**
	 * 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	CacheGetResponse findCacheById(String key) throws JsonProcessingException;

	/**
	 * 패턴과 일치하는 캐시 조회
	 * @param pattern 조회할 캐시들의 공통 패턴
	 */
	List<CacheGetResponse> findCacheList(String pattern);

	/**
	 * 전체 캐시 조회
	 */
	List<CacheGetResponse> findAllCache();

	/**
	 * 캐시 생성 및 덮어쓰기
	 * @param key 생성할 캐시의 Key
	 * @param value 생성할 캐시의 Value
	 * @param expiredTime 생성할 캐시의 만료시간
	 */
	CacheGetResponse saveOrUpdate(String key, String value, int expiredTime) throws JsonProcessingException;

	/**
	 * 캐시 삭제
	 * @param key 삭제할 캐시의 Key
	 */
	void deleteCacheById(String key);

	/**
	 * 패턴과 일치하는 캐시 삭제
	 * @param pattern 삭제할 캐시들의 공통 패턴
	 */
	void deleteCacheList(String pattern);
}
