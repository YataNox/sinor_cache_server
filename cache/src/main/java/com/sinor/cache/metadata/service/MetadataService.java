package com.sinor.cache.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinor.cache.metadata.Metadata;
import com.sinor.cache.metadata.repository.MetadataRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MetadataService {
	private final MetadataRepository optionRepository;

	@Autowired
	public MetadataService(MetadataRepository optionRepository) {
		this.optionRepository = optionRepository;
	}

	public Metadata getMetadata(String key) {
		return optionRepository.findByUrl(key).orElseThrow(() -> new RuntimeException("옵션 미발견"));
	}

	public Metadata updateExpiredTime(String key, Long expiredTime) {
		Metadata metadata = this.getMetadata(key);
		metadata.setExpiredTime(expiredTime);
		return optionRepository.save(metadata);
	}
}
