package com.sinor.cache.admin.metadata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.admin.api.service.IApiServiceV1;
import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.common.BaseResponseStatus;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.IMetadataServiceV1;

@RestController
public class MetadataController implements IMetadataControllerV1{

	private final IMetadataServiceV1 metadataService;
	private final IApiServiceV1 cacheService;

	@Autowired
	public MetadataController(IMetadataServiceV1 metadataService, IApiServiceV1 cacheService) {
		this.metadataService = metadataService;
		this.cacheService = cacheService;
	}

	/**
	 * @param path 조회할 옵션의 path
	 */
	@Override
	@GetMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> getMetadata(@RequestParam("path") String path) {
		try {
			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.SUCCESS, metadataService.findMetadataById(path));
		}catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * Metadata 목록 조회, 10개 씩 Paging
	 * @param page 목록의 Page 번호
	 */
	@Override
	@GetMapping("/admin/metadata/all")
	public BaseResponse<List<MetadataGetResponse>> getMetadataAll(@RequestParam("page") int page) {
		// 조회할 Metadata Page 설정 1 Page 당 데이터 10개
		PageRequest pageRequest = PageRequest.of(page, 10);
		return new BaseResponse<List<MetadataGetResponse>>(BaseResponseStatus.SUCCESS, metadataService.findAll(pageRequest));
	}

	/**
	 * @param path 생성할 옵션의 path
	 */
	@Override
	@PostMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> createMetadata(@RequestParam("path") String path) {
		try {
			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.SUCCESS, metadataService.createMetadata(path, 60*10L));
		}catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * @param path 수정할 옵션의 path
	 */
	@Override
	@PutMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> updateMetadata(@RequestParam("path") String path, @RequestParam("newExpiredTime") Long newExpiredTime) {
		try {
			// 캐시 수정
			MetadataGetResponse updatedMetadata = metadataService.updateMetadata(path, newExpiredTime);
			
			// 수정된 Path URL 캐시 목록 삭제
			cacheService.deleteCacheList(updatedMetadata.getMetadataUrl());

			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.SUCCESS, updatedMetadata);
		}catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * @param path 삭제할 옵션의 path
	 */
	@DeleteMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> deleteMetadata(@RequestParam("path") String path) {
		try {
			metadataService.deleteMetadataById(path);
			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.NO_CONTENT);
		} catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	/**
	 * 해당 path의 옵션이 있는지 확인
	 * @param path 유무를 파악할 path 값
	 */
	@Override
	public BaseResponse<Boolean> isExistMetadata(String path) {
		return new BaseResponse<>(BaseResponseStatus.SUCCESS, metadataService.isExistById(path));
	}
}
