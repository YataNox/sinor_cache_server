package com.sinor.cache.config;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinor.cache.admin.metadata.Metadata;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.MetadataService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostConstructService {
	// 앱 실행 시 빈 등록 이후 바로 처리해야하는 내용들을 처리하기 위한 서비스 클래스
	private final MetadataService metadataService;

	public PostConstructService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	@PostConstruct
	public void loadMetadataToCache(){
		log.info("Metadata 초기 세팅 실행");
		// Mysql에 저장된 Metadata 조회
		List<Metadata> metadataList = metadataService.findAll();

		for(Metadata metadata : metadataList){
			MetadataGetResponse response = metadataService.getMetadataCache(metadata.getMetadataUrl());
		}
		log.info("Metadata Cache Create.");
	}
}
