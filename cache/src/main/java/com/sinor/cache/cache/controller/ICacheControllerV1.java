package com.sinor.cache.cache.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ICacheControllerV1 {

	/**
	 * 단일 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	@GetMapping("/admin/cache")
	ResponseEntity<?> getCache(@RequestParam("key") String key);

	/**
	 * URL 별 캐시 목록 조회
	 * @param url 조회할 캐시들의 공통 url 값
	 */
	@GetMapping("/admin/cache/list")
	ResponseEntity<?> getCacheListByKeyParams(@RequestParam("url") String url);

	/**
	 * 전체 캐시 목록 조회
	 */
	@GetMapping("/admin/cache/list/all")
	ResponseEntity<?> getCacheListAll();
}
