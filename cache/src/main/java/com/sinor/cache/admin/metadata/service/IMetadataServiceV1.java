package com.sinor.cache.admin.metadata.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.common.CustomException;

public interface IMetadataServiceV1 {
	/**
	 * 옵션 조회 없으면 기본 10분 생성 후 반환
	 * @param path 조회할 옵션의 path
	 */
	MetadataGetResponse findOrCreateMetadataById(String path) throws CustomException;

	/**
	 * 옵션 조회 없으면 예외 발생
	 * @param path 조회할 옵션의 path
	 */
	MetadataGetResponse findMetadataById(String path) throws CustomException;

	/**
	 * 옵션 수정
	 * @param path 옵션 변경할 path 값
	 * @param newExpiredTime 새로 적용할 만료시간
	 */
	MetadataGetResponse updateMetadata(String path, Long newExpiredTime) throws CustomException;

	/**
	 * 옵션 삭제
	 *
	 * @param path 삭제할 path
	 */
	void deleteMetadataById(String path) throws CustomException;

	/**
	 * 옵션 생성
	 * @param path 생성할 path 값
	 * @param expiredTime 적용할 만료시간
	 */
	MetadataGetResponse	createMetadata(String path, Long expiredTime) throws CustomException;

	/**
	 * 옵션들의 목록을 조회한다. (10개씩 페이징)
	 * @param pageRequest 조회할 목록의 size, page 번호가 들어 있는 Paging 클래스
	 */
	List<MetadataGetResponse> findAllByPage(PageRequest pageRequest);

	/**
	 * 옵션이 있는 지 확인
	 * @param path 유무를 확인할 path 값
	 */
	Boolean isExistById(String path);
}
