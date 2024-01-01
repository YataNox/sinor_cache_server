package com.sinor.cache.common;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.sinor.cache.admin.metadata.Metadata;
import com.sinor.cache.admin.metadata.service.MetadataService;
import com.sinor.cache.utils.JsonToStringConverter;
import com.sinor.cache.utils.RedisUtils2;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MetadataCacheInitializer implements ApplicationRunner {
	private final MetadataService metadataService;
	private final JsonToStringConverter jsonToStringConverter;
	private final RedisUtils2 metadataRedisUtils;
	public MetadataCacheInitializer(MetadataService metadataService, JsonToStringConverter jsonToStringConverter, RedisUtils2 metadataRedisUtils) {
		this.metadataService = metadataService;
		this.jsonToStringConverter = jsonToStringConverter;
		this.metadataRedisUtils = metadataRedisUtils;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 애플리케이션 실행 후 Mysql의 Metadata redis 캐시 저장
		List<Metadata> list = metadataService.findAll();

		for(Metadata metadata : list){
			String cacheData = jsonToStringConverter.objectToJson(metadata);
			metadataRedisUtils.setRedisData(metadata.getMetadataUrl(), cacheData);
			log.info(metadata.getMetadataUrl() + " Cache Created : " + LocalDateTime.now());
		}
	}
}
