package com.sinor.main.product.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    private String info;
    private String thumbnail;
    private String subTitle;
    @Column(name = "product_name")
    private String productName;
    private int price;
    @Column(name = "discount_price")
    private int discountPrice;
    @Column(name = "option_id")
    private int optionId;
    private String company;

}
