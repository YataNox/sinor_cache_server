package com.sinor.cache.main.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sinor.cache.common.BaseException;
import com.sinor.cache.main.model.MainCacheResponse;
import org.springframework.util.MultiValueMap;

public interface IMainCacheServiceV1 {
	/**
	 * Main 서버에 요청을 보내는 메서드
	 * @param path 요청 path
	 * @param queryString 요청 queryString
	 */
	String getMainPathData(String path, MultiValueMap<String, String> queryString) throws JsonProcessingException;

	/**
	 * 캐시에 데이터가 있는지 확인하고 없으면 데이터를 조회해서 있으면 데이터를 조회해서 반환해주는 메소드
	 * opsForValue() - Strings를 쉽게 Serialize / Deserialize 해주는 Interface
	 * @param path
	 * @return
	 */
	String getDataInCache(String path);

	/**
	 * 캐시에 데이터가 없으면 메인 데이터를 조회해서 캐시에 저장하고 반환해주는 메소드
	 * @param path 검색할 캐시의 Path
	 * @param queryString 각 캐시의 구별을 위한 QueryString
	 */
	MainCacheResponse postInCache(String path, MultiValueMap<String, String> queryString) throws BaseException;
}
