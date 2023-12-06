package com.sinor.cache.stroage.service;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinor.cache.stroage.response.CacheGetResponse;

@Service
@Transactional
public class CacheService implements ICacheService{
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	@Autowired
	public CacheService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
	}


	@Override
	public CacheGetResponse findCacheById(String key) throws JsonProcessingException {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		return objectMapper.readValue(ops.get(key), CacheGetResponse.class);
	}

	@Override
	public List<CacheGetResponse> findCacheList(String pattern) {
		List<CacheGetResponse> list = new ArrayList<>();

		redisTemplate.executeWithStickyConnection(
			connection -> {
				ScanOptions options = ScanOptions.scanOptions().match("*" + pattern + "*").build();
				Cursor<byte[]> cursor = connection.scan(options);

				while (cursor.hasNext()) {
					byte[] keyBytes = cursor.next();
					String key = new String(keyBytes, UTF_8);
					try {
						list.add(objectMapper.readValue(key, CacheGetResponse.class));
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				}

				cursor.close();

				return null;
			});

		return list;
	}

	@Override
	public List<CacheGetResponse> findAllCache() { // 수많은 오류로 인해 임시 삭제 
		// 구조를 다시 정해서 작성해야함
		return null;
	}

	@Override
	public CacheGetResponse saveOrUpdate(String key, String value, int expiredTime) throws JsonProcessingException {
		redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.SECONDS);
		return objectMapper.readValue(redisTemplate.opsForValue().get(key), CacheGetResponse.class);
	}

	@Override
	public void deleteCacheById(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void deleteCacheList(String pattern) {
		// scan으로 키 조회
		Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
			connection -> {
				ScanOptions options = ScanOptions.scanOptions().match("*" + pattern + "*").build();
				return connection.scan(options);
			});

		try {
			// unlink로 키 삭제
			redisTemplate.executePipelined((RedisCallback<Object>)connection -> {
				while (cursor.hasNext()) {
					byte[] key = cursor.next();
					connection.unlink(key);
				}
				return null;
			});

			System.out.println("관련 키 전부 삭제 : " + pattern);
		} finally {
			cursor.close();
		}
	}




	/**
	 * 캐시 생성 함수
	 * @param key 캐시 조회 시 활용할 Key 값
	 * @param value 해당 Key에 매칭되는 value 값, 메인 서버에서의 실반환 값이 저장
	 * @param expiredTime 해당 캐시의 만료 시간, Second를 기준으로 계산한다.
	 *//*
	public void setWithExpiration(String key, String value, Long expiredTime) {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		ops.set(key, value, expiredTime, TimeUnit.SECONDS);
	}

	*//**
	 * key의 만료 기간 변경 함수 (현재는 하나의 key를 수정하는 형태)
	 * 해당 기능을 살려는 두되, 상위 URL 옵션 값 변경시 캐시를 삭제할 것이기에 실 사용 여부는 의문
	 * @param key 만료 기간 변경할 Key 이름
	 * @param newExpirationTime 새로 적용할 만료기간
	 *//*
	public void updateExpirationTime(String key, long newExpirationTime) {
		// Redis의 EXPIRE 명령을 사용하여 만료 시간을 변경
		redisTemplate.expire(key, newExpirationTime, TimeUnit.SECONDS);
	}

	*//**
	 * 해당 Key를 조회하는 함수
	 * @param key 조회할 캐시의 Key 이름
	 * @return 해당 캐시의 Value 값이 반환 된다.
	 *//*
	public String get(String key) {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		return ops.get(key);
	}

	*//**
	 * 특정 키를 찾는 함수
	 * 매개 변수로 받은 Key 값의 패턴의 키 목록을 조회하여 반환
	 * Keys를 사용하는 메서드라 Scan 사용등으로 최소한의 성능 이슈만 처리 필요
	 * @param key 조회할 키들의 패턴 값
	 * @return 패턴에 해당하는 Key들을 Set 형태로 반환
	 *//*
	public Set<String> getKeys(String key) {
		Set<String> keys = redisTemplate.keys("*" + key + "*");
		return keys;
	}

	*//**
	 * 특정 Key의 만료 시간을 조회하는 함수
	 * 캐시가 만료되기까지 남은 시간을 조회한다.
	 * @param key 만료시간을 조회할 Key 이름
	 * @return 해당 Key의 만료 시간
	 *//*
	public Long getExpireTime(String key) {
		return redisTemplate.getExpire(key);
	}

	//sean 코드 병합

	*//**
	 * 특정 문자열을 포함하는 key의 만료 기간 변경 함수
	 * @param keySubstring 만료시간을 조회할 Key 이름
	 * @param newExpirationTime 새로 적용할 만료시간
	 *//*
	@Transactional
	public void updateExpirationTimeByScan(String keySubstring, long newExpirationTime) {

		Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
			connection -> connection.scan(ScanOptions.scanOptions().match("*" + keySubstring + "*").build())
		);

		try {
			while (cursor.hasNext()) {
				byte[] keyBytes = cursor.next();
				String matchingKey = new String(keyBytes, UTF_8);

				redisTemplate.expire(matchingKey, newExpirationTime, TimeUnit.SECONDS);
			}

			System.out.println("문자열을 포함한 TTL 변경 : " + keySubstring);
		} finally {
			cursor.close();
		}
	}

	*//**
	 * 특정 Key를 삭제하는 함수
	 * @param key 삭제할 데이터의 key
	 *//*
	@Transactional
	public void deleteData(String key) {
		redisTemplate.delete(key);
	}

	*//**
	 * 특정 문자열을 포함하는 key를 삭제하는 함수
	 * keys, unlink를 사용
	 * @param searchString 삭제할 키에 포함될 문자열
	 *//*
	@Transactional
	public void deleteDataContainingStringByKeys(String searchString) {
		Set<String> keys = redisTemplate.keys("*" + searchString + "*");

		if (keys != null && !keys.isEmpty()) {
			redisTemplate.executePipelined((RedisCallback<Object>)connection -> {
				for (String key : keys) {
					connection.unlink(key.getBytes(UTF_8));
				}
				return null;
			});

			System.out.println("문자열이 포함된 키 전부 삭제 : " + searchString);
		} else {
			System.out.println("문자열이 포함된 데이터X : " + searchString);
		}
	}

	*//**
	 * 특정 문자열을 포함하는 key를 삭제하는 함수
	 * scan, unlink를 사용
	 * @param searchString 삭제할 키에 포함될 문자열
	 *//*
	@Transactional
	public void deleteDataContainingStringByScan(String searchString) {
		// scan으로 키 조회
		Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
			connection -> {
				ScanOptions options = ScanOptions.scanOptions().match("*" + searchString + "*").build();
				return connection.scan(options);
			});

		try {
			// unlink로 키 삭제
			redisTemplate.executePipelined((RedisCallback<Object>)connection -> {
				while (cursor.hasNext()) {
					byte[] key = cursor.next();
					connection.unlink(key);
				}
				return null;
			});

			System.out.println("관련 키 전부 삭제 : " + searchString);
		} finally {
			cursor.close();
		}
	}

	*//*    @Transactional
	public void deleteDataContainingStringByScan_2(String searchString) {
		Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
				redisConnection -> redisConnection.scan(ScanOptions.scanOptions().match("*" + searchString + "*").build())
		);

		try {
			while (cursor.hasNext()) {
				redisTemplate.unlink(new String(cursor.next(), StandardCharsets.UTF_8));
			}

			System.out.println("문자열이 포함된 키 전부 삭제 : " + searchString);
		} finally {
			cursor.close();
		}
	}
*//*
	public Set<String> searchDataContainingString(String searchString) {
		Set<String> keys = new HashSet<>();

		redisTemplate.executeWithStickyConnection(
			connection -> {
				ScanOptions options = ScanOptions.scanOptions().match("*" + searchString + "*").build();
				Cursor<byte[]> cursor = connection.scan(options);

				while (cursor.hasNext()) {
					byte[] keyBytes = cursor.next();
					String key = new String(keyBytes, UTF_8);
					keys.add(key);
				}

				cursor.close();

				return null;
			});

		return keys;
	}*/
}
