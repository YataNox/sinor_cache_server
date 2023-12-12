package com.sinor.cache.admin.api.controller;

import java.util.List;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.common.AdminResponse;
public interface IApiControllerV1 {
	/**
	 * 단일 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	AdminResponse<ApiGetResponse> getCache(String key);

	/**
	 * URL 별 캐시 목록 조회
	 * @param url 조회할 캐시들의 공통 url 값
	 */
	AdminResponse<List<ApiGetResponse>> getCacheListByKeyParams(String url);

	/**
	 * 전체 캐시 목록 조회
	 */
	AdminResponse<List<ApiGetResponse>> getCacheListAll();
}
