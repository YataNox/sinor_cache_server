package com.example.demo.src.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRes {
    private Integer product_id;
    private String product_name;
    private String product_price;
    private String product_comment;
    private String product_phone;
}
