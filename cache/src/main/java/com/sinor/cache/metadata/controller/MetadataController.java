package com.sinor.cache.metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.cache.metadata.model.MetadataGetResponse;
import com.sinor.cache.metadata.service.IMetadataServiceV1;

@RestController
public class MetadataController implements IMetadataControllerV1{

	private final IMetadataServiceV1 metadataService;

	@Autowired
	public MetadataController(IMetadataServiceV1 metadataService) {
		this.metadataService = metadataService;
	}

	@Override
	public ResponseEntity<?> getMetadata(String path) {
		MetadataGetResponse response = null;
		try {
			response = metadataService.findMetadataById(path);
		}catch (RuntimeException e){
			return ResponseEntity.internalServerError().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<?> createMetadata(String path) {
		return null;
	}

	@Override
	public ResponseEntity<?> updateMetadata(String path) {
		return null;
	}

	@Override
	public ResponseEntity<?> deleteMetadata(String path) {
		return null;
	}
}
