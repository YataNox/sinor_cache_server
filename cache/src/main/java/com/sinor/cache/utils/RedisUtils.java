package com.sinor.cache.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtils {
	private final RedisTemplate<String, String> redisTemplate;

	public RedisUtils(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String getRedisData(String key) {
		if(!isExist(key))
			return null;

		return redisTemplate.opsForValue().get(key).trim();
	}

	public void setRedisData(String key, String value, Long ttl){
		redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
	}

	public Boolean isExist(String key){
		return redisTemplate.hasKey(key);
	}
}
