package com.sinor.cache.metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.metadata.service.IMetadataService;

@RestController
public class MetadataController implements IMetadataController{

	private final IMetadataService metadataService;

	@Autowired
	public MetadataController(IMetadataService metadataService) {
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
