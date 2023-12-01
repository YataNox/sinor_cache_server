package com.example.demo.src.jdbctemplate.orderdetail.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderDetailRes {
    private int id;
    private Integer order_id;
    private Integer product_detail_id;
    private Integer order_detail_unit_price;
    private Integer order_detail_quantity;
    private Integer order_detail_discount;
    private Integer order_detail_price;
    private String order_detail_status;
    private Integer delivery_id;
}
