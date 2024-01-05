package com.sinor.cache.common;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis 메시지가 감지 됬을 때 이벤트를 처리할 메소드
 */
@Service
@Slf4j
public class CacheMessage implements MessageListener {

	private final RedisTemplate<String, String> resourceRedisTemplate;

	public CacheMessage(@Qualifier("redisTemplate") RedisTemplate<String, String> resourceRedisTemplate) {
		this.resourceRedisTemplate = resourceRedisTemplate;
	}

	/**
	 * Redis 메세지 수신 시 실행 이벤트
	 * @param message message must not be {@literal null}.
	 * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("pattern : " + new String(pattern));
		System.out.println("Redis Message : " + message);
		switch (new String(pattern)) {
			case "__keyevent@0__:del" -> writeLogDel(message);
			case "__keyevent@0__:expired" -> writeLogExpired(message);
		}
	}

	private void writeLogExpired(Message keyName){
		log.info("Received Redis expiration event for key: " + keyName);
	}

	private void writeLogDel(Message keyName){
		log.info("Received Redis del event for key: " + keyName);
	}
}