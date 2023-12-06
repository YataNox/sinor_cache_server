package com.sinor.cache.utils.dataInitialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinor.cache.stroage.CacheService;

@Service
public class DataInitializationService {

	private final CacheService redisCacheService;

	@Autowired
	public DataInitializationService(CacheService redisCacheService) {
		this.redisCacheService = redisCacheService;
	}

	public void initializeData() {
		for (int i = 0; i < 10; i++) {
			String key = "" + i;
			String value = "dummyValue" + i;

			// Adjust the expiration time as needed
			redisCacheService.setWithExpiration(key, value, 1000L);
		}
	}
}
