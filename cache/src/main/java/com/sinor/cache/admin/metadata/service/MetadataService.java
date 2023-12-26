package com.sinor.cache.admin.metadata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.cache.admin.metadata.Metadata;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.repository.MetadataRepository;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MetadataService implements IMetadataServiceV1 {
	private final MetadataRepository metadataRepository;

	@Autowired
	public MetadataService(MetadataRepository optionRepository) {
		this.metadataRepository = optionRepository;
	}

	/**
	 * 옵션 조회 없으면 기본 10분 생성 후 반환
	 * @param path 조회할 옵션의 path
	 */
	@Override
	public MetadataGetResponse findOrCreateMetadataById(String path) throws CustomException {
		System.out.println(path);
		// 옵션 조회, 없으면 기본 10분으로 Metadata 생성
		Optional<Metadata> metadata = metadataRepository.findById(path);

		if(metadata.isEmpty())
			return MetadataGetResponse.from(
				metadataRepository.save(Metadata.defaultValue(path))
			);

		// response 반환
		return MetadataGetResponse.from(metadata.get());
	}

	/**
	 * 옵션 조회 없으면 예외 발생
	 * @param path 조회할 옵션의 path
	 */
	@Override
	public MetadataGetResponse findMetadataById(String path) throws CustomException {
		// 옵션 조회
		/*Optional<Metadata> metadata = metadataRepository.findById(path);

		if(metadata.isEmpty())
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);*/

		// response 반환
		return getMetadataCache(path);
	}

	/**
	 * 옵션 수정
	 * @param path 옵션 변경할 path 값
	 * @param newExpiredTime 새로 적용할 만료시간
	 */
	@Override
	@CachePut(cacheNames = "MetadataCache", key = "#path")
	public MetadataGetResponse updateMetadata(String path, Long newExpiredTime) throws CustomException {

		// 해당 url 유무 파악
		Optional<Metadata> metadata = metadataRepository.findById(path);

		if(metadata.isEmpty())
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);

		// 변경 값으로 저장
		// response 반환
		return MetadataGetResponse.from(
			metadataRepository.save(
				Metadata.updateValue(metadata.get(), newExpiredTime)
			)
		);
	}

	/**
	 * 옵션 삭제
	 * @param path 삭제할 path
	 */
	@Override
	public void deleteMetadataById(String path) throws CustomException {
		// 유무 파악
		if(!metadataRepository.existsById(path))
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);

		// 캐시 삭제
		metadataRepository.deleteById(path);

	}

	/**
	 * 옵션 생성
	 * @param path 생성할 path 값
	 * @param expiredTime 적용할 만료시간
	 */
	@Override
	public MetadataGetResponse createMetadata(String path, Long expiredTime) throws CustomException {
		// url 옵션이 이미 있는지 조회
		if(metadataRepository.existsById(path))
			throw new RuntimeException("해당 옵션 값이 있습니다..");

		// 옵션 생성
		Metadata metadata = metadataRepository.save(
			Metadata.createValue(path, expiredTime)
		);

		// response 반환
		return MetadataGetResponse.from(metadata);
	}

	/**
	 * 옵션들의 목록을 조회한다. (10개씩 페이징)
	 * @param pageRequest 조회할 목록의 size, page 번호가 들어 있는 Paging 클래스
	 */
	@Override
	public List<MetadataGetResponse> findAllByPage(PageRequest pageRequest) {
		return metadataRepository.findAll(pageRequest).stream().map(MetadataGetResponse::from).toList();
	}

	/**
	 * page 상관없이 Metadata 전체를 조회하는 메소드
	 * 초기 세팅 이외의 사용 비권장
	 */
	public List<Metadata> findAll(){
		return metadataRepository.findAll();
	}

	/**
	 * 옵션이 있는 지 확인
	 * @param path 유무를 확인할 path 값
	 */
	@Override
	public Boolean isExistById(String path) {
		return metadataRepository.existsById(path);
	}

	@Cacheable(value = "MetadataCache", key = "#path")
	public MetadataGetResponse getMetadataCache(String path){
		log.info("캐시 없음. 메소드 동작");
		Optional<Metadata> metadata = metadataRepository.findById(path);

		if(metadata.isEmpty())
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);

		return MetadataGetResponse.from(metadata.get());
	}
}