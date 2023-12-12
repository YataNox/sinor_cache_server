package com.sinor.cache.admin.metadata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.admin.api.service.IApiServiceV1;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.IMetadataServiceV1;
import com.sinor.cache.common.AdminResponse;
import com.sinor.cache.common.ResponseStatus;

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
	public ResponseEntity<AdminResponse<MetadataGetResponse>> getMetadata(String path) {
		AdminResponse<MetadataGetResponse> adminResponse = new AdminResponse<>(ResponseStatus.SUCCESS, metadataService.findMetadataById(path));
		return ResponseEntity.status(ResponseStatus.SUCCESS.getCode()).body(adminResponse);
	}

	/**
	 * Metadata 목록 조회, 10개 씩 Paging
	 *
	 * @param page 목록의 Page 번호
	 */
	@Override
	public ResponseEntity<AdminResponse<List<MetadataGetResponse>>> getMetadataAll(int page) {
		// 조회할 Metadata Page 설정 1 Page 당 데이터 10개
		PageRequest pageRequest = PageRequest.of(page, 10);
		AdminResponse<List<MetadataGetResponse>> adminResponse = new AdminResponse<>(ResponseStatus.SUCCESS, metadataService.findAll(pageRequest));
		return ResponseEntity.status(ResponseStatus.SUCCESS.getCode()).body(adminResponse);
	}

	/**
	 * @param path 생성할 옵션의 path
	 */
	@Override
	public ResponseEntity<AdminResponse<MetadataGetResponse>> createMetadata(String path) {
		AdminResponse<MetadataGetResponse> adminResponse = new AdminResponse<>(ResponseStatus.SUCCESS, metadataService.createMetadata(path, 60*10L));
		return ResponseEntity.status(ResponseStatus.SUCCESS.getCode()).body(adminResponse);
	}

	/**
	 * @param path 수정할 옵션의 path
	 */
	@Override
	public ResponseEntity<AdminResponse<MetadataGetResponse>> updateMetadata(String path, Long newExpiredTime) {
		// 캐시 수정
		MetadataGetResponse updatedMetadata = metadataService.updateMetadata(path, newExpiredTime);
		AdminResponse<MetadataGetResponse> adminResponse = new AdminResponse<>(ResponseStatus.SUCCESS, updatedMetadata);
		// 수정된 Path URL 캐시 목록 삭제
		cacheService.deleteCacheList(updatedMetadata.getMetadataUrl());

		return ResponseEntity.status(ResponseStatus.SUCCESS.getCode()).body(adminResponse);
	}

	/**
	 * @param path 삭제할 옵션의 path
	 */
	@Override
	public ResponseEntity<AdminResponse<?>> deleteMetadata(String path) {
		metadataService.deleteMetadataById(path);
		AdminResponse<MetadataGetResponse> adminResponse = new AdminResponse<>(ResponseStatus.NO_CONTENT);
		return ResponseEntity.status(ResponseStatus.NO_CONTENT.getCode()).body(adminResponse);
	}

	/**
	 * 해당 path의 옵션이 있는지 확인
	 *
	 * @param path 유무를 파악할 path 값
	 */
	@Override
	public ResponseEntity<AdminResponse<Boolean>> isExistMetadata(String path) {
		AdminResponse<Boolean> adminResponse = new AdminResponse<>(ResponseStatus.SUCCESS, metadataService.isExistById(path));
		return ResponseEntity.status(ResponseStatus.SUCCESS.getCode()).body(adminResponse);
	}
}
