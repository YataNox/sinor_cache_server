package com.sinor.cache.metadata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.admin.service.ICacheServiceV1;
import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.common.BaseResponseStatus;
import com.sinor.cache.metadata.model.MetadataGetResponse;
import com.sinor.cache.metadata.service.IMetadataServiceV1;

@RestController
public class MetadataController implements IMetadataControllerV1{

	private final IMetadataServiceV1 metadataService;
	private final ICacheServiceV1 cacheService;

	@Autowired
	public MetadataController(IMetadataServiceV1 metadataService, ICacheServiceV1 cacheService) {
		this.metadataService = metadataService;
		this.cacheService = cacheService;
	}

	@Override
	@GetMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> getMetadata(@RequestParam("path") String path) {
		try {
			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.SUCCESS, metadataService.findMetadataById(path));
		}catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	@Override
	@GetMapping("/admin/metadata/all")
	public BaseResponse<List<MetadataGetResponse>> getMetadataAll(@RequestParam("page") int page) {
		// 조회할 Metadata Page 설정 1 Page 당 데이터 10개
		PageRequest pageRequest = PageRequest.of(page, 10);
		return new BaseResponse<List<MetadataGetResponse>>(BaseResponseStatus.SUCCESS, metadataService.findAll(pageRequest));
	}

	@Override
	@PostMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> createMetadata(@RequestParam("path") String path) {
		try {
			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.SUCCESS, metadataService.createMetadata(path, 60*10L));
		}catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

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

	@DeleteMapping("/admin/metadata")
	public BaseResponse<MetadataGetResponse> deleteMetadata(@RequestParam("path") String path) {
		try {
			metadataService.deleteMetadataById(path);
			return new BaseResponse<MetadataGetResponse>(BaseResponseStatus.NO_CONTENT);
		} catch (BaseException e) {
			return new BaseResponse<>(e.getStatus());
		}
	}

	@Override
	public BaseResponse<Boolean> isExistMetadata(String path) {
		return new BaseResponse<>(BaseResponseStatus.SUCCESS, metadataService.isExistById(path));
	}
}
