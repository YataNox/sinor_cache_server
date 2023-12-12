package com.sinor.cache.admin.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.admin.api.service.IApiServiceV1;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.AdminResponse;
import com.sinor.cache.common.ResponseStatus;

@RestController
public class ApiController implements IApiControllerV1 {
	// 해당 컨트롤러의 API 구조는 캐시의 Key 값에 의해 수정될 필요가 있음
	private final IApiServiceV1 cacheService;

	@Autowired
	public ApiController(IApiServiceV1 cacheService) {
		this.cacheService = cacheService;
	}

	/**
	 * 단일 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	@Override
	@GetMapping("/admin/cache")
	public AdminResponse<ApiGetResponse> getCache(@RequestParam("key") String key) {

		try {
			return new AdminResponse<ApiGetResponse>(ResponseStatus.SUCCESS, cacheService.findCacheById(key));
		} catch (CustomException e) {
			return new AdminResponse<>(e.getStatus());
		}
    }

	/**
	 * URL 별 캐시 목록 조회
	 * @param url 조회할 캐시들의 공통 url 값
	 */
	@Override
	@GetMapping("/admin/cache/list")
	public AdminResponse<List<ApiGetResponse>> getCacheListByKeyParams(@RequestParam("url") String url) {
		try {
			return new AdminResponse<List<ApiGetResponse>>(ResponseStatus.SUCCESS, cacheService.findCacheList(url));
		} catch (CustomException e) {
			return new AdminResponse<>(e.getStatus());
		}
	}

	/**
	 * 전체 캐시 목록 조회
	 */
	@Override
	@GetMapping("/admin/cache/list/all")
	public AdminResponse<List<ApiGetResponse>> getCacheListAll() {
		return null;
	}


	/**
	 * API로 캐시를 생성
	 * value 값을 현재 Path로 받는 멍청한 짓을 하고 있음 RequestBody로 수정 필요 (멍청한 챗 GPT)
	 * @param key
	 * @param value
	 * @param expirationTime
	 * @return
	 *//*
	@PostMapping("/setCache/{key}/{value}/{expirationTime}")
	public String setCache(
		@PathVariable String key,
		@PathVariable String value,
		@PathVariable long expirationTime
	) {
		cacheService.setWithExpiration(key, value, expirationTime);
		return "Cache set successfully!";
	}

	*//**
	 * URL 만료기간 재설정
	 * @param key ex) /userAccount
	 * @param queryString ex) ?id=12 or /12
	 * @param newExpirationTime
	 * @return 변경 성공 메시지
	 *//*
	@PutMapping("/updateExpirationTime/{key}/{queryString}/{newExpirationTime}")
	public String updateExpirationTime(
		@PathVariable String key,
		@PathVariable long queryString,
		@PathVariable long newExpirationTime
	) {
		// mysql 값 수정 후 하위 캐시 초기화
		metadataService.updateExpiredTime(key, newExpirationTime);
		// 현재 하위 캐시들에 대한 삭제 코드가 없음 추가 요망
		return "Expiration time updated successfully!";
	}

	*//**
	 * 매개변수 Key의 패턴과 유사한 캐시들의 목록을 조회
	 * @param key 찾을 Key의 패턴
	 * @return key 값을 포함하는 Key들 SET 목록
	 *//*
	@GetMapping("/getKeys/{key}")
	public String getKeys(@PathVariable String key) {
		Set<String> value = cacheService.getKeys(key);
		for (String s : value) {
			System.out.println(s);
		}
		return "Cached value: " + value;
	}

	// sean 코드 추가

	*//**
	 * URL 만료기간 재설정
	 * @param key 키를 조회할 때 특정 문자열
	 * @param queryString ex) ?id=12 or /12
	 * @param newExpirationTime
	 * @return 변경 성공 메시지
	 *//*
	@PutMapping("/updateExpirationTimeByScan/{key}/{queryString}/{newExpirationTime}")
	public String updateExpirationTimeByScan(
		@PathVariable String key,
		@PathVariable long queryString,
		@PathVariable long newExpirationTime
	) {
		cacheService.updateExpirationTimeByScan(key + queryString, newExpirationTime);

		return "Expiration time updated successfully!";
	}

	*//*    /**
	 * 매개변수 Key의 패턴과 유사한 캐시들을 삭제(Scan 성능이 더 좋음)
	 * @param key 찾을 Key의 패턴
	 * @return
	 *//**//*
    @PostMapping("/deleteDataContainingStringByKeys/{key}")
    public String deleteDataContainingStringByKeys(@PathVariable String key) {
        cacheService.deleteDataContainingStringByKeys(key);

        return "Delete Cache: " + key;
    }*//*

	*//**
	 * 매개변수 Key의 패턴과 유사한 캐시들을 삭제
	 * @param key 찾을 Key의 패턴
	 * @return
	 *//*
	@PostMapping("/deleteDataContainingStringByScan/{key}")
	public String deleteData(@PathVariable String key) {
		cacheService.deleteData(key);

		return "Delete Cache";
	}

	*//**
	 * 매개변수 Key의 패턴과 유사한 캐시들을 삭제
	 * @param url 찾을 Key의 패턴
	 * @return
	 *//*
	@PostMapping("/deleteDataContainingStringByScan/{url}")
	public String deleteDataContainingStringByScan(@PathVariable String url) {
		cacheService.deleteDataContainingStringByScan(url);

		return "Delete Cache";
	}

	*//**

	 *//*
	@GetMapping("/searchDataContainingString/{url}")
	public Set<String> searchDataContainingString(@PathVariable String url) {
		return cacheService.searchDataContainingString(url);
	}*/
}