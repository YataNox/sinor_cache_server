package com.sinor.cache.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.sinor.cache.common.CustomException;
import com.sinor.cache.common.ResponseStatus;

public class RedisUtils {
	private final RedisTemplate<String, String> redisTemplate;

	public RedisUtils(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String getRedisData(String key) throws CustomException{
		try {
			return redisTemplate.opsForValue().get(key).trim();
		} catch (NullPointerException e){
			throw new CustomException(ResponseStatus.CACHE_NOT_FOUND);
		}
	}

	public void setRedisData(String key, String value, Long ttl){
		redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
	}

	public Boolean isExist(String key) throws CustomException {
		try {
			System.out.println(redisTemplate.hasKey(key));
			return redisTemplate.hasKey(key);
		} catch (NullPointerException e){
			throw new CustomException(ResponseStatus.CACHE_NOT_FOUND);
		}
	}

	public Cursor<byte[]> searchPatternKeys(String pattern){

		return redisTemplate.executeWithStickyConnection(connection -> {
			ScanOptions options = ScanOptions.scanOptions().match("*" + pattern + "*").build();
			return connection.scan(options);
		});
	}

	public Boolean deleteCache(String key) throws CustomException {
		return redisTemplate.delete(key);
	}

	public void unlinkCache(String key) throws CustomException{
		redisTemplate.unlink(key);
	}
}
