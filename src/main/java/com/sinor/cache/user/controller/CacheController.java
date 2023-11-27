package com.example.demo.src.user.controller;

import com.example.demo.src.user.service.RedisCacheService;
import com.example.demo.src.user.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    private final RedisCacheService cacheService;
    private final OptionService optionService;

    @Autowired
    public CacheController(RedisCacheService cacheService, OptionService optionService) {
        this.cacheService = cacheService;
        this.optionService = optionService;
    }

    @GetMapping("/setCache/{key}/{value}/{expirationTime}")
    public String setCache(
            @PathVariable String key,
            @PathVariable String value,
            @PathVariable long expirationTime
    ) {
        cacheService.setWithExpiration(key, value, expirationTime);
        return "Cache set successfully!";
    }

    @PutMapping("/updateExpirationTime/{key}/{queryString}/{newExpirationTime}")
    public String updateExpirationTime(
            @PathVariable String key,
            @PathVariable long queryString,
            @PathVariable long newExpirationTime
    ) {
        // mysql 값 수정 후, 캐시 만료기간 초기화
        // key 값과 QueryString 분리 작업이 필요함, 현재는 땜빵으로 url상에서 분리했음
        // ex) db에는 appProduct, cache에서는 appProduct2가 변경되어야함 appProducts
        optionService.updateExpiredTime(key, newExpirationTime); // appProducts
        cacheService.updateExpirationTime(key + queryString, newExpirationTime); // appProducts1, appProducts2
        return "Expiration time updated successfully!";
    }
    @GetMapping("/getCache/{key}")
    public String getCache(@PathVariable String key) {
        String value = cacheService.get(key);
        System.out.println(cacheService.getExpireTime(key));
        return "Cached value: " + value;
    }
}