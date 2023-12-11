package com.sinor.cache.admin.metadata.controller;

import java.util.List;

import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.common.BaseResponse;

public interface IMetadataControllerV1 {
	/**
	 * @param path 조회할 옵션의 path
	 */
	BaseResponse<MetadataGetResponse> getMetadata(String path);

	/**
	 * Metadata 목록 조회, 10개 씩 Paging
	 * @param page 목록의 Page 번호
	 */
	BaseResponse<List<MetadataGetResponse>> getMetadataAll(int page);

	/**
	 * @param path 생성할 옵션의 path
	 */
	BaseResponse<MetadataGetResponse> createMetadata(String path);

	/**
	 * @param path 수정할 옵션의 path
	 */
	BaseResponse<MetadataGetResponse> updateMetadata(String path, Long newExpiredTime);

	/**
	 * @param path 삭제할 옵션의 path
	 */
	BaseResponse<MetadataGetResponse> deleteMetadata(String path);

	/**
	 * 해당 path의 옵션이 있는지 확인
	 * @param path 유무를 파악할 path 값
	 */
	BaseResponse<Boolean> isExistMetadata(String path);

}
