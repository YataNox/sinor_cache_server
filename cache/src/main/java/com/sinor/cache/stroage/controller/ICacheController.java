package com.sinor.cache.stroage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ICacheController {

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
	ResponseEntity<?> getUrlCacheList(@RequestParam("url") String url);

	/**
	 * 전체 캐시 목록 조회
	 */
	@GetMapping("/admin/cache/list/all")
	ResponseEntity<?> getAllCacheList();

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@GetMapping("/admin/metadata")
	ResponseEntity<?> getMetadata(@RequestParam("url") String url);

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@PostMapping("/admin/metadata")
	ResponseEntity<?> createMetadata(@RequestParam("url") String url);

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@PutMapping("/admin/metadata")
	ResponseEntity<?> updateMetadata(@RequestParam("url") String url);

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@DeleteMapping("/admin/metadata")
	ResponseEntity<?> deleteMetadata(@RequestParam("url") String url);
}
