package com.sinor.cache.admin.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.common.AdminResponse;
@RestController
public interface IApiControllerV1 {
	/**
	 * 단일 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	@GetMapping("/admin/cache")
	AdminResponse<ApiGetResponse> getCache(@RequestParam String key);

	/**
	 * URL 별 캐시 목록 조회
	 * @param url 조회할 캐시들의 공통 url 값
	 */
	@GetMapping("/admin/cache/list")
	AdminResponse<List<ApiGetResponse>> getCacheListByKeyParams(@RequestParam String url);

	/**
	 * 전체 캐시 목록 조회
	 */
	@GetMapping("/admin/cache/list/all")
	AdminResponse<List<ApiGetResponse>> getCacheListAll();
}
