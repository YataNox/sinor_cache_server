package com.sinor.cache.admin.metadata.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.common.AdminResponse;

public interface IMetadataControllerV1 {
	/**
	 * @param path 조회할 옵션의 path
	 */
	@GetMapping("/admin/metadata")
	ResponseEntity<AdminResponse<MetadataGetResponse>> getMetadata(@RequestParam String path);

	/**
	 * Metadata 목록 조회, 10개 씩 Paging
	 *
	 * @param page 목록의 Page 번호
	 */
	@GetMapping("/admin/metadata/all")
	ResponseEntity<AdminResponse<List<MetadataGetResponse>>> getMetadataAll(@RequestParam int page);

	/**
	 * @param path 생성할 옵션의 path
	 */
	@PostMapping("/admin/metadata")
	ResponseEntity<AdminResponse<MetadataGetResponse>> createMetadata(@RequestParam String path);

	/**
	 * @param path 수정할 옵션의 path
	 */
	@PutMapping("/admin/metadata")
	ResponseEntity<AdminResponse<MetadataGetResponse>> updateMetadata(@RequestParam String path, @RequestParam Long newExpiredTime);

	/**
	 * @param path 삭제할 옵션의 path
	 */
	@DeleteMapping("/admin/metadata")
	ResponseEntity<AdminResponse<?>> deleteMetadata(@RequestParam String path);

	/**
	 * 해당 path의 옵션이 있는지 확인
	 *
	 * @param path 유무를 파악할 path 값
	 */
	@GetMapping("/admin/metadata/exist")
	ResponseEntity<AdminResponse<Boolean>> isExistMetadata(@RequestParam String path);

}
