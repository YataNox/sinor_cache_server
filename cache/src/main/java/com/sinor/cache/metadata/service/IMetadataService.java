package com.sinor.cache.metadata.service;

import org.springframework.stereotype.Service;

import com.sinor.cache.metadata.model.MetadataGetResponse;

@Service
public interface IMetadataService {
	/**
	 * 옵션 조회
	 * @param url 조회할 옵션의 url
	 */
	MetadataGetResponse findMetadataById(String url);

	/**
	 * 옵션 수정
	 * @param url 옵션 변경할 url 값
	 * @param newExpiredTime 새로 적용할 만료시간
	 */
	MetadataGetResponse updateMetadata(String url, Long newExpiredTime);

	/**
	 * 옵션 삭제
	 *
	 * @param url 삭제할 url 값
	 * @return
	 */
	Boolean deleteMetadataById(String url);

	/**
	 * 옵션 생성
	 * @param url 생성할 url 값
	 * @param expiredTime 적용할 만료시간
	 */
	MetadataGetResponse	createMetadata(String url, Long expiredTime);
}
