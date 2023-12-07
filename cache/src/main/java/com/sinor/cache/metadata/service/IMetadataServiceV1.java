package com.sinor.cache.metadata.service;

import org.springframework.stereotype.Service;

import com.sinor.cache.metadata.model.MetadataGetResponse;

@Service
public interface IMetadataServiceV1 {
	/**
	 * 옵션 조회
	 * @param path 조회할 옵션의 path
	 */
	MetadataGetResponse findMetadataById(String path);

	/**
	 * 옵션 수정
	 * @param path 옵션 변경할 path 값
	 * @param newExpiredTime 새로 적용할 만료시간
	 */
	MetadataGetResponse updateMetadata(String path, Long newExpiredTime);

	/**
	 * 옵션 삭제
	 *
	 * @param path 삭제할 path
	 */
	Boolean deleteMetadataById(String path);

	/**
	 * 옵션 생성
	 * @param path 생성할 path 값
	 * @param expiredTime 적용할 만료시간
	 */
	MetadataGetResponse	createMetadata(String path, Long expiredTime);
}
