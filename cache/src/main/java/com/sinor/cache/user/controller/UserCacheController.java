package com.sinor.cache.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.user.model.UserCacheResponse;
import com.sinor.cache.user.service.UserCacheService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserCacheController implements IUserCacheControllerV1<UserCacheResponse, UserCacheResponse> {
	private final UserCacheService userCacheService;

	/** 미완성
	 * @param path        요청에 전달된 path
	 * @param queryString 요청에 전달된 queryString
	 * @return
	 */
	@Override
	@GetMapping("/{path}")
	@ResponseBody
	public BaseResponse<?> getDataReadCache(@PathVariable String path,
		@RequestParam(required = false) String queryString) {
		try {
			String pathCache = userCacheService.getDataInCache(path);
			if (pathCache == null) {
				return new BaseResponse<UserCacheResponse>(userCacheService.postInCache(path, queryString));
			}

			return new BaseResponse<>(pathCache);
		} catch (BaseException e) {
			System.out.println(e.getMessage());
			return new BaseResponse<>(e.getStatus());
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