package com.sinor.cache.cache.controller;

import com.sinor.cache.cache.model.CacheGetResponse;
import com.sinor.cache.common.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface ICacheControllerV1 {

	/**
	 * 단일 캐시 조회
	 *
	 * @param key 조회할 캐시의 Key 값
	 */
	@GetMapping("/admin/cache")
	BaseResponse<CacheGetResponse> getCache(@RequestParam("key") String key);

	/**
	 * URL 별 캐시 목록 조회
	 *
	 * @param url 조회할 캐시들의 공통 url 값
	 */
	@GetMapping("/admin/cache/list")
	BaseResponse<List<CacheGetResponse>> getCacheListByKeyParams(@RequestParam("url") String url);

	/**
	 * 전체 캐시 목록 조회
	 */
	@GetMapping("/admin/cache/list/all")
	BaseResponse<List<CacheGetResponse>> getCacheListAll();
}
