package com.sinor.cache.admin.api.service;

import static com.sinor.cache.common.ResponseStatus.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.cache.admin.api.model.ApiGetResponse;
import com.sinor.cache.common.CustomException;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;
import com.sinor.cache.utils.RedisUtils2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ApiService implements IApiServiceV1 {
	private final JsonToStringConverter jsonToStringConverter;
	private final RedisUtils2 metadataRedisUtils;

	private final RedisUtils responseRedisUtils;

	@Autowired
	public ApiService(JsonToStringConverter jsonToStringConverter, RedisUtils2 metadataRedisUtils,
		RedisUtils responseRedisUtils) {
		this.metadataRedisUtils = metadataRedisUtils;
		this.jsonToStringConverter = jsonToStringConverter;
		this.responseRedisUtils = responseRedisUtils;
	}

	/**
	 * 캐시 조회
	 * @param key 조회할 캐시의 Key 값
	 */
	public ApiGetResponse findCacheById(String key) throws CustomException {
		String value = responseRedisUtils.getRedisData(key);

		if (value.isBlank())
			throw new CustomException(CACHE_NOT_FOUND);

		return jsonToStringConverter.jsontoClass(value, ApiGetResponse.class);
	}

	/**
	 * 패턴과 일치하는 캐시 조회
	 * @param pattern 조회할 캐시들의 공통 패턴
	 */
	@Override
	public List<ApiGetResponse> findCacheList(String pattern) throws CustomException {
		List<ApiGetResponse> list = new ArrayList<>();

		Cursor<byte[]> cursor = responseRedisUtils.searchPatternKeys(pattern);

		processCursor(cursor, list);

		if (list.isEmpty())
			throw new CustomException(CACHE_LIST_NOT_FOUND);

		return list;
	}

	/**
	 * 전체 캐시 조회
	 */
	@Override
	public List<ApiGetResponse> findAllCache() { // 수많은 오류로 인해 임시 삭제
		// 구조를 다시 정해서 작성해야함
		return null;
	}

	/**
	 * 캐시 생성 및 덮어쓰기
	 * @param key 생성할 캐시의 Key
	 * @param value 생성할 캐시의 Value
	 * @param expiredTime 생성할 캐시의 만료시간
	 */
	@Override
	@Transactional
	public ApiGetResponse saveOrUpdate(String key, String value, Long expiredTime) throws CustomException {
		responseRedisUtils.setRedisData(key, value, expiredTime);
		return jsonToStringConverter.jsontoClass(responseRedisUtils.getRedisData(key), ApiGetResponse.class);
	}

	/**
	 * 캐시 삭제
	 * @param key 삭제할 캐시의 Key
	 */
	@Override
	public Boolean deleteCacheById(String key) throws CustomException {
		return responseRedisUtils.deleteCache(key);
	}

	/**
	 * 패턴과 일치하는 캐시 삭제
	 * @param pattern 삭제할 캐시들의 공통 패턴
	 */
	@Override
	public void deleteCacheList(String pattern) throws CustomException {
		// scan으로 키 조회
		Cursor<byte[]> cursor = responseRedisUtils.searchPatternKeys(pattern);

		if (cursor == null)
			throw new CustomException(CACHE_LIST_NOT_FOUND);

		// unlink로 키 삭제
		while (cursor.hasNext()) {
			responseRedisUtils.unlinkCache(new String(cursor.next(), UTF_8));
		}
	}

	/**
	 * RedisTemplate에서 얻은 byte Cursor 값을 CacheGetResponse List 형태로 담아 반환하는 메소드
	 * @param cursor Redis에서 조회로 얻은 Byte 값
	 * @param list cursor를 역직렬화해서 넣어줄 List 객체
	 * @throws CustomException 역직렬화 시 JsonProcessingException이 발생했을 때 Throw될 BaseException
	 */
	private void processCursor(Cursor<byte[]> cursor, List<ApiGetResponse> list) throws CustomException {
		while (cursor.hasNext()) {
			byte[] keyBytes = cursor.next();
			String key = new String(keyBytes, UTF_8);

			String jsonValue = responseRedisUtils.getRedisData(key);
			list.add(jsonToStringConverter.jsontoClass(jsonValue, ApiGetResponse.class));
		}
	}

}
