package com.sinor.cache.product;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sinor.cache.common.BaseException;
import com.sinor.cache.common.BaseResponseStatus;
import com.sinor.cache.product.ProductDao;
import com.sinor.cache.product.ProductRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor //생성자
public class ProductService {

	private final ProductDao productDao;

	//GET
	@Cacheable(value = "productId", key = "#productId")
	public ProductRes getProductDeatilById(int productId) throws BaseException {
		slowQuery(3000);
		try {
			ProductRes getProductRes = productDao.getProductDeatilById(productId);
			return getProductRes;
		} catch (Exception exception) {
			throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
		}
	}

	//    //POST
	//    public PostAdminProductRes insertProductDetail(PostAdminProductRes postAdminProductRes) throws BaseException {
	//        try{
	//            PostAdminProductRes insertedProductDetail = productDao.insertProductDetail(postAdminProductRes);
	//            return new PostAdminProductRes(productId);
	//        } catch (Exception exception) {
	//            throw new BaseException(DATABASE_ERROR);
	//        }
	//    }

	private void slowQuery(long seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
