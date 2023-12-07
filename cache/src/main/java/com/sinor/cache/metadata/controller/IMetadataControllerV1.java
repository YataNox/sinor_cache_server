package com.sinor.cache.metadata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IMetadataControllerV1 {
	/**
	 *
	 * @param path 조회할 옵션의 path
	 */
	@GetMapping("/admin/metadata")
	ResponseEntity<?> getMetadata(@RequestParam("path") String path);

	/**
	 *
	 * @param path 조회할 옵션의 path
	 */
	@PostMapping("/admin/metadata")
	ResponseEntity<?> createMetadata(@RequestParam("path") String path);

	/**
	 *
	 * @param path 조회할 옵션의 path
	 */
	@PutMapping("/admin/metadata")
	ResponseEntity<?> updateMetadata(@RequestParam("path") String path);

	/**
	 *
	 * @param path 조회할 옵션의 path
	 */
	@DeleteMapping("/admin/metadata")
	ResponseEntity<?> deleteMetadata(@RequestParam("path") String path);

}
