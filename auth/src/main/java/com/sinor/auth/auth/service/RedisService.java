package com.sinor.auth.auth.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService implements IRedisServiceV1 {
	/**
	 * 	엑세스토큰과 리프래시 토큰의 발급은 성공적이였으나, 레디스에 저장 시킬 때 레디스에 저장이 안되는 문제가 생겼다.
	 * 	그 후 레디스에서 리프래시 토큰값을 가져와서 검사를 한후 새로운 엑세스토큰을 발급해주는데 거기서 레디스에 저장된 리프래시 토큰값이 없다고 나온다.(레디스에 똑바로 저장이 안되는것)
	 *
	 */
	private final RedisTemplate<String, String> redisTemplate;

	@Transactional
	public void setValues(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	// 만료시간 설정 -> 자동 삭제
	@Transactional
	public void setValuesWithTimeout(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
	}

	public String getValues(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Transactional
	public void deleteValues(String key) {
		redisTemplate.delete(key);
	}
}
