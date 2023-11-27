package com.example.demo.src.controller;


import com.example.demo.common.BaseException;
import com.example.demo.common.BaseResponse;
import com.example.demo.src.config.RedisCacheService;
import com.example.demo.src.dto.ProductRes;
import com.example.demo.src.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/app/products")
public class ProductController {

    private final ProductService productService;
    private final RedisCacheService cacheService;

    @ResponseBody
    @GetMapping("/{productId}") // (GET) localhost:8080/app/products/{productId}
    public BaseResponse<ProductRes> getProductDeatilById(@PathVariable("productId") int productId) {
        String cachedData = cacheService.get("appProducts" + productId); // appProductsproductId -> Ex)appProductId1
        ObjectMapper mapper = new ObjectMapper(); // JSON 형식을 사용할 때, 응답들을 직렬화하고 요청들을 역직렬화 할 때 사용하는 기술
        // 검색한 Cache가 있으면 
        if (cachedData != null) {
            try {
                // String 값 GetProductRes로 역직렬화
                ProductRes readValue = mapper.readValue(cachedData, ProductRes.class);
                return new BaseResponse<>(readValue);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else { // Cache가 없으면
            log.info("product 실행 : id/" + productId);
            long start = System.currentTimeMillis();      // 실행 시간 측정 시작
            try {
                // 데이터 베이스에서 값 조회
                ProductRes getProductRes = productService.getProductDeatilById(productId);
                long end = System.currentTimeMillis(); // 실행 시간 측정 종료
                log.info(productId + "Cache 수행시간 : " + (end - start));

                String jsonString = mapper.writeValueAsString(getProductRes); // 조회한 값 직렬화
                // key와 조회한 value로 Cache 설정
                cacheService.setWithExpiration("appProducts" + productId, jsonString);

                // 조회한 값 리턴
                return new BaseResponse<>(getProductRes);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    @ResponseBody
//    @PostMapping("/{productId}/admin/productdetail")
//    public BaseResponse<GetProductRes> insertProductDetail(PostAdminProductRes postAdminProductRes)
}
