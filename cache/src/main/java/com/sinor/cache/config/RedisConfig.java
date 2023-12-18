package com.sinor.cache.config;

import java.time.Duration;
import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils;

@Configuration
public class RedisConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory("redisHost", 6379); // 여러 다른 Redis 연결 방법이 있을 수 있습니다.
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(new CacheMessageListener());
		container.addMessageListener(listenerAdapter, new ChannelTopic("__keyevent@0__:expired"));

		return container;
	}

	public static class CacheMessageListener {

		public void handleMessage(String message) {
			System.out.println("Received Redis expiration event for key: " + message);
			// 여기서 캐시 만료 이벤트 처리 로직을 수행
		}
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisTemplate<String, String> redisTemplate) {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper())))   // json 형식으로 value 저장
			.computePrefixWith(CacheKeyPrefix.simple())
			.entryTtl(Duration.ofMinutes(10))
			.disableCachingNullValues();

		return RedisCacheManager.builder(Objects.requireNonNull(redisTemplate.getConnectionFactory()))
			.cacheDefaults(cacheConfig)
			.build();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper;
	}

	@Bean
	public JsonToStringConverter jsonToStringConverter(){
		return new JsonToStringConverter(objectMapper());
	}

	@Bean
	public RedisUtils redisUtils(){
		return new RedisUtils(redisTemplate(redisConnectionFactory()));
	}
}
