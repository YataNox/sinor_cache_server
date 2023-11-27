package com.example.demo.src.user.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    private Long baseexpirationTime = 30L;

    private final RedisTemplate<String, String> redisTemplate;

    public RedisCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setWithExpiration(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, baseexpirationTime, TimeUnit.SECONDS);
    }

    public void updateExpirationTime(String key, long newExpirationTimeInSeconds) {
        // Redis의 EXPIRE 명령을 사용하여 만료 시간을 변경
        updateExpireTime(newExpirationTimeInSeconds);
        redisTemplate.expire(key, baseexpirationTime, TimeUnit.SECONDS);
    }

    public String get(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public Long getExpireTime(String key){
        Long ops = redisTemplate.getExpire(key);
        return ops;
    }

    public void updateExpireTime(Long newBaseExpiredTime){
        baseexpirationTime = newBaseExpiredTime;
        System.out.println("Expiration updated successfully!");
    }
}
