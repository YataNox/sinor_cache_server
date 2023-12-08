package com.sinor.cache.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.user.model.UserCacheResponse;
import com.sinor.cache.user.service.UserCacheService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserCacheController implements IUserCacheControllerV1<UserCacheResponse, UserCacheResponse> {

	private final UserCacheService userCacheService;

	@Override
	@GetMapping("/{path}")
	@ResponseBody
	public UserCacheResponse getDataReadCache(@PathVariable String path,
		@RequestParam(required = false) String queryString) {
		try {
			System.out.println("path: " + path + ", queryString: " + queryString);
			return userCacheService.fetchDataAndStoreInCache(path, queryString);
		} catch (Exception e) {
			e.fillInStackTrace();
			return null;
		}
	}

	@Override
	public UserCacheResponse postDataReadCache(String path, String queryString, UserCacheResponse body) {
		return null;
	}

	@Override
	public UserCacheResponse deleteDataRefreshCache(String path, String queryString, UserCacheResponse body) {
		return null;
	}

	@Override
	public UserCacheResponse updateDataRefreshCache(String path, String queryString, UserCacheResponse body) {
		return null;
	}
}
