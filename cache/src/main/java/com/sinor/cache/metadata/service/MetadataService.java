package com.sinor.cache.metadata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponseStatus;
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
	public MetadataGetResponse findOrCreateMetadataById(String path) throws BaseException {
		// 옵션 조회, 없으면 기본 10분으로 Metadata 생성
		Metadata metadata = metadataRepository.findById(path)
			.orElse(
				metadataRepository.save(
					Metadata.builder()
						.metadataUrl(path)
						.metadataTtlSecond(600L)
						.build()
				)
			);

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public MetadataGetResponse findMetadataById(String path) throws BaseException {
		// 옵션 조회, 없으면 기본 10분으로 Metadata 생성
		Metadata metadata = metadataRepository.findById(path)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.DATA_NOT_FOUND));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public MetadataGetResponse updateMetadata(String path, Long newExpiredTime) throws BaseException {
		// 해당 url 유무 파악
		if(!metadataRepository.existsById(path))
			throw new BaseException(BaseResponseStatus.DATA_NOT_FOUND);

		// 변경 값으로 저장
		Metadata metadata = metadataRepository.save(new Metadata(path, newExpiredTime));

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public void deleteMetadataById(String path) throws BaseException{
		// 유무 파악
		if(!metadataRepository.existsById(path))
			throw new BaseException(BaseResponseStatus.DATA_NOT_FOUND);
		
		// 캐시 삭제
		metadataRepository.deleteById(path);

	}

	@Override
	public MetadataGetResponse createMetadata(String path, Long expiredTime) throws BaseException{
		// url 옵션이 이미 있는지 조회
		if(metadataRepository.existsById(path))
			throw new RuntimeException("해당 옵션 값이 있습니다..");
		
		// 옵션 생성
		Metadata metadata = metadataRepository.save(
			Metadata.builder()
				.metadataUrl(path)
				.metadataTtlSecond(expiredTime)
				.build()
		);

		// response 반환
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.build();
	}

	@Override
	public List<MetadataGetResponse> findAll(PageRequest pageRequest) {
		return metadataRepository.findAll(pageRequest).stream().map(value -> MetadataGetResponse.builder()
			.metadataUrl(value.getMetadataUrl())
			.metadataTtlSecond(value.getMetadataTtlSecond())
			.build()).toList();
	}

	@Override
	public Boolean isExistById(String path) {
		return metadataRepository.existsById(path);
	}
}
