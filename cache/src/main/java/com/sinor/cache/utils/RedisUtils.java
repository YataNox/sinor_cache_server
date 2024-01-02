package com.sinor.cache.utils;

import static com.sinor.cache.common.admin.AdminResponseStatus.*;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.sinor.cache.common.admin.AdminException;

public class RedisUtils {
	private final RedisTemplate<String, String> redisTemplate;

	public RedisUtils(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * redistemplate 안의 값을 호출
	 * @param key 호출할 캐시의 key 값
	 * @return 해당 key 값의 value (String 형태)
	 * @throws AdminException 역 직렬화 실패 시 발생 오류
	 */
	public String getRedisData(String key) throws AdminException {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (NullPointerException e) {
			throw new AdminException(CACHE_NOT_FOUND);
		}
	}

	/**
	 * 만료 시간 있는 캐시 저장
	 * @param key 생성할 캐시의 key
	 * @param value 생성할 캐시의 value
	 * @param ttl 생성할 캐시의 만료 시간 (Second)
	 */
	public void setRedisData(String key, String value, Long ttl) {
		redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
	}

	/**
	 * 만료 시간 없는 캐시 저장
	 * @param key
	 * @param value
	 */
	public void setRedisData(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 캐시가 있는지 확인
	 * @param key 캐시 유무를 확인 할 캐시의 key
	 * @return 있으면 true 없으면 false
	 */
	public Boolean isExist(String key) throws AdminException {
		try {
			return redisTemplate.hasKey(key);
		} catch (NullPointerException e) {
			throw new AdminException(CACHE_NOT_FOUND);
		}
	}

	/**
	 * 일정 패턴을 가진 캐시 목록을 조회
	 * scan을 사용해서 비동기적으로 작동
	 * @param pattern 찾으려는 key의 일부
	 * @return 찾은 key들의 Cursor
	 */
	//TODO ScanOptions의 deprecated로 인해 Redis KeyCommands으로 리팩토링 필요
	public Cursor<byte[]> searchPatternKeys(String pattern) {

		return redisTemplate.executeWithStickyConnection(connection -> {
			ScanOptions options = ScanOptions.scanOptions().match("*" + pattern + "*").build();
			return connection.scan(options);
		});
	}

	public String disuniteKey(String key) {

		if (key.contains("?")) {
			return key.substring(0, key.indexOf("?"));
		} else {
			return key;
		}

	}

	public Boolean deleteCache(String key) throws AdminException {
		return redisTemplate.delete(key);
	}

	public void unlinkCache(String key) throws AdminException {
		redisTemplate.unlink(key);
	}
}
