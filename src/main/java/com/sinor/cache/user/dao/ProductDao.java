package com.example.demo.src.user.dao;

import com.example.demo.src.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ProductRes getProductDeatilById(int productId){
        String getProductQuery = "SELECT product.*, product_detail.*\n" +
                "FROM product\n" +
                "JOIN product_detail ON product.product_id = product_detail.product_id\n" +
                "WHERE product.product_id = ?;";
        
        int getProductParams = productId;

        return this.jdbcTemplate.queryForObject(getProductQuery,
                (rs, rowNum) -> new ProductRes(
                        rs.getInt("product_id"),
                        rs.getString("Product_name"),
                        rs.getString("Product_price"),
                        rs.getString("Product_comment"),
                        rs.getString("Product_phone")),
                getProductParams);
    }
}
