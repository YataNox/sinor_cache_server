package com.sinor.cache.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinor.cache.metadata.Metadata;
import com.sinor.cache.metadata.repository.MetadataRepository;
import com.sinor.cache.metadata.response.MetadataGetResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MetadataService implements IMetadataService {
	private final MetadataRepository metadataRepository;

	@Autowired
	public MetadataService(MetadataRepository optionRepository) {
		this.metadataRepository = optionRepository;
	}

	public Metadata getMetadata(String key) {
		return metadataRepository.findByUrl(key).orElseThrow(() -> new RuntimeException("옵션 미발견"));
	}

	/*public Metadata updateExpiredTime(String key, Long expiredTime) {
		Metadata metadata = this.getMetadata(key);
		metadata.setExpiredTime(expiredTime);
		return metadataRepository.save(metadata);
	}*/

	@Override
	public MetadataGetResponse findMetadataById(String url) {
		return null;
	}

	@Override
	public MetadataGetResponse updateMetadata(String url, int newExpiredTime) {
		return null;
	}

	@Override
	public void deleteMetadataById(String url) {

	}

	@Override
	public MetadataGetResponse createMetadata(String url, int ExpiredTime) {
		return null;
	}
}
