package com.sinor.cache.metadata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IMetadataController {
	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@GetMapping("/admin/metadata")
	ResponseEntity<?> getMetadata(@RequestParam("url") String url);

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@PostMapping("/admin/metadata")
	ResponseEntity<?> createMetadata(@RequestParam("url") String url);

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@PutMapping("/admin/metadata")
	ResponseEntity<?> updateMetadata(@RequestParam("url") String url);

	/**
	 *
	 * @param url 조회할 옵션의 URL 값
	 */
	@DeleteMapping("/admin/metadata")
	ResponseEntity<?> deleteMetadata(@RequestParam("url") String url);

}
