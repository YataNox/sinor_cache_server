package com.example.demo.src.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    private final Map<String, Long> cacheExpirationTimes;




    public RedisCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.cacheExpirationTimes = new HashMap<>();

    }

    // 캐시를 생성한다.
    public void setWithExpiration(String key, String value, Long expiredTime) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, expiredTime, TimeUnit.SECONDS);
    }

    
    // key값이랑 초값을 받아서 해당 key의 만료기간 변경 함수
    public void updateExpirationTime(String key, long newExpirationTime) {
        // Redis의 EXPIRE 명령을 사용하여 만료 시간을 변경
        redisTemplate.expire(key, newExpirationTime, TimeUnit.SECONDS);
    }

    public String get(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public Long getExpireTime(String key) {
        Long ops = redisTemplate.getExpire(key);
        return ops;
    }
}
