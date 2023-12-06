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
public class MetadataService implements IMetadataService {
	private final MetadataRepository metadataRepository;

	@Autowired
	public MetadataService(MetadataRepository optionRepository) {
		this.metadataRepository = optionRepository;
	}

	/*public Metadata getMetadata(String key) {
		return metadataRepository.findByUrl(key).orElseThrow(() -> new RuntimeException("옵션 미발견"));
	}*/

	/*public Metadata updateExpiredTime(String key, Long expiredTime) {
		Metadata metadata = this.getMetadata(key);
		metadata.setExpiredTime(expiredTime);
		return metadataRepository.save(metadata);
	}*/

	@Override
	public MetadataGetResponse findMetadataById(String url) throws RuntimeException {
		// 옵션 조회
		Metadata metadata = metadataRepository.findById(url)
			.orElseThrow(() -> new RuntimeException("해당 옵션 값이 없습니다."));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public MetadataGetResponse updateMetadata(String url, Long newExpiredTime) throws RuntimeException {
		// 해당 url 유무 파악
		if(!metadataRepository.existsById(url))
			throw new RuntimeException("해당 옵션 값이 없습니다.");

		// 변경 값으로 저장
		Metadata metadata = metadataRepository.save(new Metadata(url, newExpiredTime));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public Boolean deleteMetadataById(String url) throws RuntimeException{
		// 유무 파악
		if(!metadataRepository.existsById(url))
			throw new RuntimeException("해당 옵션 값이 없습니다.");
		
		// 캐시 삭제
		metadataRepository.deleteById(url);
		
		return true;
	}

	@Override
	public MetadataGetResponse createMetadata(String url, Long expiredTime) throws RuntimeException{
		// url 옵션이 이미 있는지 조회
		if(metadataRepository.existsById(url))
			throw new RuntimeException("해당 옵션 값이 있습니다..");
		
		// 옵션 생성
		Metadata metadata = metadataRepository.save(new Metadata(url, expiredTime));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}
}
