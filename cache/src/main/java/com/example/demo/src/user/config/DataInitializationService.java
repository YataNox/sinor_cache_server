package com.example.demo.src.user.config;

import com.example.demo.src.user.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService {

    private final RedisCacheService redisCacheService;

    @Autowired
    public DataInitializationService(RedisCacheService redisCacheService) {
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
