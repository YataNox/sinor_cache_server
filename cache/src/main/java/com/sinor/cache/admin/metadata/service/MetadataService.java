package com.sinor.cache.admin.metadata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.cache.admin.metadata.Metadata;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.repository.MetadataRepository;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MetadataService implements IMetadataServiceV1 {
	private final MetadataRepository metadataRepository;
	private final JsonToStringConverter jsonToStringConverter;

	private final RedisUtils metadataRedisUtils;

	@Autowired
	public MetadataService(MetadataRepository optionRepository, JsonToStringConverter jsonToStringConverter,
		RedisUtils metadataRedisUtils) {
		this.metadataRepository = optionRepository;
		this.jsonToStringConverter = jsonToStringConverter;
		this.metadataRedisUtils = metadataRedisUtils;
	}

	/**
	 * 옵션 조회 없으면 기본 10분 생성 후 반환
	 * @param path 조회할 옵션의 path
	 */
	@Override
	public MetadataGetResponse findOrCreateMetadataById(String path) throws CustomException {
		// 캐시 검사
		if(metadataRedisUtils.isExist(path)) {
			Metadata cacheMetadata = jsonToStringConverter.jsontoClass(metadataRedisUtils.getRedisData(path),
				Metadata.class);
			log.info("Get Metadata Cache : " + cacheMetadata.getMetadataUrl());
			return MetadataGetResponse.from(cacheMetadata);
		}

		System.out.println(path + " Cache 미발견 Mysql 호출");
		// 옵션 조회, 없으면 기본 10분으로 Metadata 생성
		Optional<Metadata> metadata = metadataRepository.findById(path);

		if(metadata.isEmpty())
			return createMetadata(path);

		// response 반환
		return MetadataGetResponse.from(metadata.get());
	}

	/**
	 * 옵션 조회 없으면 예외 발생
	 * @param path 조회할 옵션의 path
	 */
	@Override
	public MetadataGetResponse findMetadataById(String path) throws CustomException {
		// 캐시 검사
		if(metadataRedisUtils.isExist(path)) {
			Metadata cacheMetadata = jsonToStringConverter.jsontoClass(metadataRedisUtils.getRedisData(path),
				Metadata.class);

			return MetadataGetResponse.from(cacheMetadata);
		}

		// 옵션 조회
		long startTime = System.currentTimeMillis();
		System.out.println("조회 시작");
		Optional<Metadata> metadata = metadataRepository.findById(path);
		long endTime = System.currentTimeMillis();
		System.out.println("조회 종료 : " + (endTime - startTime) + "밀리초");

		if(metadata.isEmpty())
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);

		// response 반환
		return MetadataGetResponse.from(metadata.get());
	}

	/**
	 * 옵션 수정
	 * @param path 옵션 변경할 path 값
	 * @param newExpiredTime 새로 적용할 만료시간
	 */
	@Override
	@CachePut(cacheNames = "metadataCacheInfo", key = "#path")
	public MetadataGetResponse updateMetadata(String path, Long newExpiredTime) throws CustomException {

		// 해당 url 유무 파악
		long startTime = System.currentTimeMillis();
		Optional<Metadata> metadata = metadataRepository.findById(path);
		long endTime = System.currentTimeMillis();
		System.out.println("조회 종료 : " + (endTime - startTime) + "밀리초");

		if(metadata.isEmpty())
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);

		// 변경 값으로 저장
		Metadata saveMetadata = metadataRepository.save(
			Metadata.updateValue(metadata.get().getMetadataUrl(), newExpiredTime, metadata.get().getVersion())
		);
		metadataRedisUtils.setRedisData(path, jsonToStringConverter.objectToJson(saveMetadata));
		// response 반환
		return MetadataGetResponse.from(saveMetadata);
	}

	/**
	 * 옵션 삭제
	 * @param path 삭제할 path
	 */
	@Override
	@CacheEvict(value = "metadataCacheInfo", key = "#path")
	public void deleteMetadataById(String path) throws CustomException {
		// 유무 파악
		if(!metadataRepository.existsById(path))
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);

		// 캐시 삭제
		metadataRepository.deleteById(path);
		metadataRedisUtils.deleteCache(path);
	}

	/**
	 * 옵션 생성 expriedTime 지정 가능
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

		metadataRedisUtils.setRedisData(path, jsonToStringConverter.objectToJson(metadata));
		// response 반환
		return MetadataGetResponse.from(metadata);
	}

	/**
	 * 옵션 생성 default Value를 활용
	 * @param path
	 */
	@Override
	public MetadataGetResponse createMetadata(String path) throws CustomException {
		// url 옵션이 이미 있는지 조회
		if(metadataRepository.existsById(path))
			throw new RuntimeException("해당 옵션 값이 있습니다..");

		// 옵션 생성
		Metadata metadata = metadataRepository.save(
			Metadata.defaultValue(path)
		);

		// 옵션 Redis 저장
		metadataRedisUtils.setRedisData(path, jsonToStringConverter.objectToJson(metadata));
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

	/*@Cacheable(value = "metadataCacheInfo", key = "#path")
	public String getMetadataCache(String path){
		log.info("캐시 없음. 메소드 동작");
		long startTime = System.currentTimeMillis();
		Optional<Metadata> metadata = metadataRepository.findById(path);
		long endTime = System.currentTimeMillis();
		System.out.println("조회 종료 : " + (endTime - startTime) + "밀리초");

		if(metadata.isEmpty())
			throw new CustomException(ResponseStatus.METADATA_NOT_FOUND);
		return jsonToStringConverter.objectToJson(MetadataGetResponse.from(metadata.get()));
	}*/
}