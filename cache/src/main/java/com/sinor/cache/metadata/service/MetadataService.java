package com.sinor.cache.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.cache.metadata.Metadata;
import com.sinor.cache.metadata.model.MetadataGetResponse;
import com.sinor.cache.metadata.repository.MetadataRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class MetadataService implements IMetadataServiceV1 {
	private final MetadataRepository metadataRepository;

	@Autowired
	public MetadataService(MetadataRepository optionRepository) {
		this.metadataRepository = optionRepository;
	}

	@Override
	public MetadataGetResponse findMetadataById(String path) throws RuntimeException {
		// 옵션 조회
		Metadata metadata = metadataRepository.findById(path)
			.orElseThrow(() -> new RuntimeException("해당 옵션 값이 없습니다."));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public MetadataGetResponse updateMetadata(String path, Long newExpiredTime) throws RuntimeException {
		// 해당 url 유무 파악
		if(!metadataRepository.existsById(path))
			throw new RuntimeException("해당 옵션 값이 없습니다.");

		// 변경 값으로 저장
		Metadata metadata = metadataRepository.save(new Metadata(path, newExpiredTime));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public Boolean deleteMetadataById(String path) throws RuntimeException{
		// 유무 파악
		if(!metadataRepository.existsById(path))
			throw new RuntimeException("해당 옵션 값이 없습니다.");
		
		// 캐시 삭제
		metadataRepository.deleteById(path);
		
		return true;
	}

	@Override
	public MetadataGetResponse createMetadata(String path, Long expiredTime) throws RuntimeException{
		// url 옵션이 이미 있는지 조회
		if(metadataRepository.existsById(path))
			throw new RuntimeException("해당 옵션 값이 있습니다..");
		
		// 옵션 생성
		Metadata metadata = metadataRepository.save(new Metadata(path, expiredTime));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}
}
