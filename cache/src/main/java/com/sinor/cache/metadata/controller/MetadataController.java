package com.sinor.cache.metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.metadata.service.IMetadataServiceV1;

@RestController
public class MetadataController implements IMetadataControllerV1{

	private final IMetadataServiceV1 metadataService;

	@Autowired
	public MetadataController(IMetadataServiceV1 metadataService) {
		this.metadataService = metadataService;
	}

	@Override
	public ResponseEntity<?> getMetadata(String url) {
		return null;
	}

	@Override
	public ResponseEntity<?> createMetadata(String url) {
		return null;
	}

	@Override
	public ResponseEntity<?> updateMetadata(String url) {
		return null;
	}

	@Override
	public ResponseEntity<?> deleteMetadata(String url) {
		return null;
	}
}
