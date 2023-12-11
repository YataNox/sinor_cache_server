package com.sinor.cache.metadata.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.metadata.model.MetadataGetResponse;

@RestController
public interface IMetadataControllerV1 {
	/**
	 * @param path 조회할 옵션의 path
	 */
	@GetMapping("/admin/metadata")
	BaseResponse<MetadataGetResponse> getMetadata(@RequestParam("path") String path);

	/**
	 * Metadata 목록 조회, 10개 씩 Paging
	 * @param page 목록의 Page 번호
	 */
	@GetMapping("/admin/metadata/all")
	BaseResponse<List<MetadataGetResponse>> getMetadataAll(@RequestParam("page") int page);

	/**
	 * @param path 생성할 옵션의 path
	 */
	@PostMapping("/admin/metadata")
	BaseResponse<MetadataGetResponse> createMetadata(@RequestParam("path") String path);

	/**
	 * @param path 수정할 옵션의 path
	 */
	@PutMapping("/admin/metadata")
	BaseResponse<MetadataGetResponse> updateMetadata(@RequestParam("path") String path, @RequestParam("newExpiredTime") Long newExpiredTime);

	/**
	 * @param path 삭제할 옵션의 path
	 */
	@DeleteMapping("/admin/metadata")
	BaseResponse<MetadataGetResponse> deleteMetadata(@RequestParam("path") String path);

	/**
	 * 해당 path의 옵션이 있는지 확인
	 * @param path 유무를 파악할 path 값
	 */
	@GetMapping
	BaseResponse<Boolean> isExistMetadata(@RequestParam("path") String path);

}
