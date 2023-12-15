package com.sinor.cache.admin.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sinor.cache.common.AdminSuccessResponse;

public interface IApiControllerV1 {
	/**
	 * 단일 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	@GetMapping("/admin/cache")
	ResponseEntity<AdminSuccessResponse<?>> getCache(@RequestParam String key);

	/**
	 * URL 별 캐시 목록 조회
	 * @param url 조회할 캐시들의 공통 url 값
	 */
	@GetMapping("/admin/cache/list")
	ResponseEntity<AdminSuccessResponse<?>> getCacheListByKeyParams(@RequestParam String url);

	/**
	 * 전체 캐시 목록 조회
	 */
	@GetMapping("/admin/cache/list/all")
	ResponseEntity<AdminSuccessResponse<?>> getCacheListAll();

	/**
	 * 단일 캐시 삭제
	 * @param key 삭제할 캐시의 key 값
	 */
	@DeleteMapping("/admin/cache")
	ResponseEntity<?> deletecache(@RequestParam String key);
}
