package com.sinor.cache.product.Response;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRes implements Serializable {
    private Integer product_id;
    private String product_name;
    private String product_price;
    private String product_comment;
    private String product_phone;
}
