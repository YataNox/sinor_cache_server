package com.sinor.main.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.main.common.BaseResponse;
import com.sinor.main.product.model.Product;
import com.sinor.main.product.model.ProductResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
	private final ProductService productService;

	@GetMapping
	public BaseResponse<ProductResponse> getProduct() {
		log.info("getProduct실행");
		List<Product> productList = productService.getProduct();
		return new BaseResponse<>(new ProductResponse(true, productList));
	}

}
