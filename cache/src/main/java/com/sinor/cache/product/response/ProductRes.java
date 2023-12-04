package com.sinor.cache.product.response;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRes implements Serializable {
	private Integer productId;
	private String productName;
	private String productPrice;
	private String productComment;
	private String productPhone;
}
