package com.sinor.cache.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;

public class RedisUtils2 {
	private final RedisTemplate<String, String> metadataRedisTemplate;

	public RedisUtils2(RedisTemplate<String, String> metadataRedisTemplate) {
		this.metadataRedisTemplate = metadataRedisTemplate;
	}

	/**
	 * redistemplate 안의 값을 호출
	 * @param key 호출할 캐시의 key 값
	 * @return 해당 key 값의 value (String 형태)
	 * @throws CustomException 역 직렬화 실패 시 발생 오류
	 */
	public String getRedisData(String key) throws CustomException {
		try {
			return metadataRedisTemplate.opsForValue().get(key);
		} catch (NullPointerException e) {
			throw new CustomException(ResponseStatus.CACHE_NOT_FOUND);
		}
	}

	/**
	 * 만료 시간 있는 캐시 저장
	 * @param key 생성할 캐시의 key
	 * @param value 생성할 캐시의 value
	 * @param ttl 생성할 캐시의 만료 시간 (Second)
	 */
	public void setRedisData(String key, String value, Long ttl) {
		metadataRedisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
	}

	/**
	 * 만료 시간 없는 캐시 저장
	 * @param key
	 * @param value
	 */
	public void setRedisData(String key, String value){
		metadataRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 캐시가 있는지 확인
	 * @param key 캐시 유무를 확인 할 캐시의 key
	 * @return 있으면 true 없으면 false
	 */
	public Boolean isExist(String key) throws CustomException {
		try {
			return metadataRedisTemplate.hasKey(key);
		} catch (NullPointerException e) {
			throw new CustomException(ResponseStatus.CACHE_NOT_FOUND);
		}
	}

	/**
	 * 일정 패턴을 가진 캐시 목록을 조회
	 * scan을 사용해서 비동기적으로 작동
	 * @param pattern 찾으려는 key의 일부
	 * @return 찾은 key들의 Cursor
	 */
	public Cursor<byte[]> searchPatternKeys(String pattern) {

		return metadataRedisTemplate.executeWithStickyConnection(connection -> {
			ScanOptions options = ScanOptions.scanOptions().match("*" + pattern + "*").build();
			return connection.scan(options);
		});
	}

	public Boolean deleteCache(String key) throws CustomException {
		return metadataRedisTemplate.delete(key);
	}

	public void unlinkCache(String key) throws CustomException {
		metadataRedisTemplate.unlink(key);
	}
}
