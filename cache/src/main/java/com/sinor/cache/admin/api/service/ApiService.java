package com.sinor.cache.admin.api.service;

import static com.sinor.cache.common.admin.AdminResponseStatus.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.admin.metadata.model.MetadataGetResponse;
import com.sinor.cache.admin.metadata.service.MetadataService;
import com.sinor.cache.common.admin.AdminException;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;
import com.sinor.cache.utils.URIUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ApiService implements IApiServiceV1 {

	private final JsonToStringConverter jsonToStringConverter;
	private final RedisUtils metadataRedisUtils;
	private final MetadataService metadataService;
	private final RedisUtils responseRedisUtils;

	@Autowired
	public ApiService(MetadataService metadataService, JsonToStringConverter jsonToStringConverter,
		RedisUtils metadataRedisUtils, RedisUtils responseRedisUtils) {
		this.metadataService = metadataService;
		this.metadataRedisUtils = metadataRedisUtils;
		this.jsonToStringConverter = jsonToStringConverter;
		this.responseRedisUtils = responseRedisUtils;
	}

	/**
	 * 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	public ApiGetResponse findCacheById(String key) throws AdminException {

		String versionKey = URIUtils.getUriPathQuery(key,
			metadataService.findMetadataById(responseRedisUtils.disuniteKey(key)).getVersion());

		String value = responseRedisUtils.getRedisData(versionKey);
		if (value.isBlank())
			throw new AdminException(CACHE_NOT_FOUND);

		return jsonToStringConverter.jsontoClass(value, ApiGetResponse.class);
	}

	/**
	 * 패턴과 일치하는 캐시 조회
	 * @param pattern 조회할 캐시들의 공통 패턴
	 */
	@Override
	//TODO cacheListTemplate안의 path 키 값으로 저장되어 있는 ArrayList 안 queryString들을 이용해서 원하는 목록들만 조회할 수 있도록 변경 필요
	public List<ApiGetResponse> findCacheList(String pattern) throws AdminException {
		// 예를들어 expression path에 대한 캐시 목록들을 조회한다고 가정하면
		// cacheListTemplate에서 expression key에 대한 캐시를 가져온다(value는 ArrayList)
		// 해당 리스트 안에는 expression path에 대한 캐시가 활성화된 queryString값들이 들어있다.
		// expression과 queryString, metadata의 version을  합쳐서 key값을 만든다.
		// 조합한 key 값을 이용해서 response들을 조회한다.
		List<ApiGetResponse> list = new ArrayList<>();

		Cursor<byte[]> cursor = responseRedisUtils.searchPatternKeys(pattern);

		processCursor(cursor, list);

		if (list.isEmpty())
			throw new AdminException(CACHE_NOT_FOUND);

		return list;
	}

	/**
	 * 캐시 생성 및 덮어쓰기
	 * @param key 생성할 캐시의 Key
	 * @param value 생성할 캐시의 Value
	 * @param expiredTime 생성할 캐시의 만료시간
	 */
	@Override
	@Transactional
	public ApiGetResponse saveOrUpdate(String key, String value, Long expiredTime) throws AdminException {
		// path 추출, 해당 path의 metadata 조회
		MetadataGetResponse metadata = metadataService.findMetadataById(responseRedisUtils.disuniteKey(key));
		// 조회한 값을 이용한 Versioning 된 Cache Name 추출
		key = URIUtils.getUriPathQuery(key, metadata.getVersion());

		// 캐시에 저장된 값이 있으면 수정, 없으면 생성
		responseRedisUtils.setRedisData(key, value, expiredTime);

		return jsonToStringConverter.jsontoClass(responseRedisUtils.getRedisData(key), ApiGetResponse.class);
	}

	/**
	 * 캐시 삭제
	 * @param key 삭제할 캐시의 Key
	 */
	@Override
	public Boolean deleteCacheById(String key) throws AdminException {

		String versionKey = URIUtils.getUriPathQuery(key,
			metadataService.findMetadataById(responseRedisUtils.disuniteKey(key)).getVersion());

		log.info("value of deleted key: " + responseRedisUtils.getRedisData(versionKey));
		return responseRedisUtils.deleteCache(versionKey);
	}

	/**
	 * 패턴과 일치하는 캐시 삭제
	 * @param pattern 삭제할 캐시들의 공통 패턴
	 */
	@Override
	//TODO cacheListTemplate안의 path 키 값으로 저장되어 있는 ArrayList 안 queryString들을 이용해서 원하는 목록들만 삭제할 수 있도록 변경 필요
	public void deleteCacheList(String pattern) throws AdminException {
		// scan으로 키 조회
		Cursor<byte[]> cursor = responseRedisUtils.searchPatternKeys(pattern);

		if (cursor == null)
			throw new AdminException(CACHE_NOT_FOUND);

		// unlink로 키 삭제
		while (cursor.hasNext()) {
			responseRedisUtils.unlinkCache(new String(cursor.next(), UTF_8));
		}
	}

	/**
	 *
	 * @param key 수정할 value의 키 값
	 * @param response 수정내용
	 * @return 수정된 결과값
	 */
	//TODO Redis에서 업데이트 확인, 출력을 위한 역직렬화 과정에서 오류 발생
	@Override
	public ApiGetResponse updateCacheById(String key, String response) {
		// path 추출, 해당 path의 metadata 조회
		MetadataGetResponse metadata = metadataService.findMetadataById(responseRedisUtils.disuniteKey(key));
		// 조회한 값을 이용한 Versioning 된 Cache Name 추출
		key = URIUtils.getUriPathQuery(key, metadata.getVersion());

		if (responseRedisUtils.isExist(key)) {

			// 추출한 Metadata ttl 값으로 캐시 데이터와 변경
			responseRedisUtils.setRedisData(key, response,
				metadata.getMetadataTtlSecond());

			// 변경한 데이터를 추출하여 ApiGetResponse 반환
			return jsonToStringConverter.jsontoClass(responseRedisUtils.getRedisData(key), ApiGetResponse.class);
		}

		throw new AdminException(CACHE_NOT_FOUND);
	}

	/**
	 * RedisTemplate에서 얻은 byte Cursor 값을 CacheGetResponse List 형태로 담아 반환하는 메소드
	 * @param cursor Redis에서 조회로 얻은 Byte 값
	 * @param list cursor를 역직렬화해서 넣어줄 List 객체
	 * @throws AdminException 역직렬화 시 JsonProcessingException이 발생했을 때 Throw될 BaseException
	 */
	private void processCursor(Cursor<byte[]> cursor, List<ApiGetResponse> list) throws AdminException {
		while (cursor.hasNext()) {
			byte[] keyBytes = cursor.next();
			String key = new String(keyBytes, UTF_8);

			String jsonValue = responseRedisUtils.getRedisData(key);
			list.add(jsonToStringConverter.jsontoClass(jsonValue, ApiGetResponse.class));
		}
	}
}
