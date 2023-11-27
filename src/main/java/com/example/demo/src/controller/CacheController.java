package com.example.demo.src.controller;

import com.example.demo.src.config.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    private final RedisCacheService cacheService;

    @Autowired
    public CacheController(RedisCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/setCache/{key}/{value}/{expirationTime}")
    public String setCache(
            @PathVariable String key,
            @PathVariable String value,
            @PathVariable long expirationTime
    ) {
        cacheService.setWithExpiration(key, value);
        return "Cache set successfully!";
    }

    @PutMapping("/updateExpirationTime/{key}/{newExpirationTime}")
    public String updateExpirationTime(
            @PathVariable String key,
            @PathVariable long newExpirationTime
    ) {
        cacheService.updateExpirationTime(key, newExpirationTime);
        return "Expiration time updated successfully!";
    }

    @GetMapping("/getCache/{key}")
    public String getCache(@PathVariable String key) {
        String value = cacheService.get(key);
        System.out.println(cacheService.getExpireTime(key));
        return "Cached value: " + value;
    }
}