package com.sinor.cache.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponse;
import com.sinor.cache.common.constant.GroupKey;
import com.sinor.cache.common.constant.KeyQueryString;
import com.sinor.cache.option.Option;
import com.sinor.cache.option.service.OptionService;
import com.sinor.cache.product.Response.ProductRes;
import com.sinor.cache.product.service.ProductService;
import com.sinor.cache.stroage.response.CacheRes;
import com.sinor.cache.stroage.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(GroupKey.APP_PRODUCT)
public class ProductController {

    private final ProductService productService;
    private final CacheService cacheService;
    private final OptionService optionService;

    @GetMapping("/{productId}") // (GET) localhost:8080/app/products/{productId}
    public BaseResponse<ProductRes> getProductDeatilById(@PathVariable("productId") int productId) {
        // 실행 시간 측정 시작
        log.info("product 실행 : id/" + productId);
        long start = System.currentTimeMillis();
        System.out.println("URL : " + GroupKey.APP_PRODUCT);

        // 지정된 캐시 값 조회
        String cachedData = cacheService.get(KeyQueryString.APP_PRODUCT + productId);
        // JSON 형식을 사용할 때, 응답들을 직렬화하고 요청들을 역직렬화 할 때 사용하는 ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // 검색한 Cache가 있으면
        if (cachedData != null) {
            try {
                System.out.println(cachedData);

                // String 값 GetProductRes로 역직렬화
                CacheRes readValue = mapper.registerModule(new JavaTimeModule()).readValue(cachedData, CacheRes.class);
                
                // 수행 시간 측정
                long end = System.currentTimeMillis(); // 실행 시간 측정 종료
                log.info(productId + "Cache 수행시간 : " + (end - start));
                
                // Mapper로 조회한 캐시 값을 ProductRes 형변환하여 반환
                return new BaseResponse<>(mapper.readValue(readValue.getResponse(), ProductRes.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else { // Cache가 없으면
            try {
                // 데이터 베이스에서 값 조회
                ProductRes getProductRes = productService.getProductDeatilById(productId);
                long end = System.currentTimeMillis(); // 실행 시간 측정 종료
                log.info(productId + "NonCache 수행시간 : " + (end - start));

                // 조회한 값 직렬화
                String jsonString = mapper.writeValueAsString(getProductRes);

                // 옵션 값 조회
                Option option = optionService.getOption(GroupKey.APP_PRODUCT);

                // 캐시 Response 생성
                CacheRes cacheRes = CacheRes.builder()
                        .ttl(option.getExpiredTime())
                        .createAt(LocalDateTime.now())
                        .url(GroupKey.APP_PRODUCT)
                        .response(jsonString).build();

                // 캐시 저장을 위해 캐시 Response 직렬화
                String jsonCache = mapper.registerModule(new JavaTimeModule()).writeValueAsString(cacheRes);
                System.out.println("캐시 : " + jsonCache);

                // key와 조회한 value로 Cache 설정
                cacheService.setWithExpiration(KeyQueryString.APP_PRODUCT + productId, jsonCache, option.getExpiredTime());
                System.out.println(cacheService.get(KeyQueryString.APP_PRODUCT + productId));
                // cacheService2.updateData(KeyQueryString.APP_PRODUCT + productId, jsonString, GroupKey.APP_PRODUCT);

                // 캐시 Response가 아닌 조회했던 ProductRes 리턴
                return new BaseResponse<>(getProductRes);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
