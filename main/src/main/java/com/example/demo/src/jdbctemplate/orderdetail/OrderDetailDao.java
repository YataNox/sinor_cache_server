package com.example.demo.src.jdbctemplate.orderdetail;

import com.example.demo.src.jdbctemplate.orderdetail.model.PatchOrderDetailReq;
import com.example.demo.src.jdbctemplate.orderdetail.model.PostOrderDetailReq;
import com.example.demo.src.jdbctemplate.orderdetail.model.GetOrderDetailRes;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetOrderDetailRes> getOrderDetails() {
        String getOrderDetailsQuery = "select * from coupang.ORDER_DETAIL where status !=?";
        return this.jdbcTemplate.query(getOrderDetailsQuery,
                (rs, rowNum) -> new GetOrderDetailRes(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_detail_id"),
                        rs.getInt("order_detail_unit_price"),
                        rs.getInt("order_detail_quantity"),
                        rs.getInt("order_detail_discount"),
                        rs.getInt("order_detail_price"),
                        rs.getString("order_detail_status"),
                        rs.getInt("delivery_id")),
                "INACTIVE"
        );
    }

    public List<GetOrderDetailRes> getOrderDetailsByOrderId(Integer OrderId) {
        String getOrderDetailsByOrderIdQuery = "select * from coupang.ORDER_DETAIL where order_id =? ";
        Integer getOrderDetailsByOrderIdParams = OrderId;
        return this.jdbcTemplate.query(getOrderDetailsByOrderIdQuery,
                (rs, rowNum) -> new GetOrderDetailRes(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_detail_id"),
                        rs.getInt("order_detail_unit_price"),
                        rs.getInt("order_detail_quantity"),
                        rs.getInt("order_detail_discount"),
                        rs.getInt("order_detail_price"),
                        rs.getString("order_detail_status"),
                        rs.getInt("delivery_id")),
                getOrderDetailsByOrderIdParams);
    }

    public GetOrderDetailRes getOrderDetail(int orderDetailId) {
        String getOrderDetailQuery = "select * from coupang.ORDER_DETAIL where id = ?";
        int getOrderDetailParams = orderDetailId;
        return this.jdbcTemplate.queryForObject(getOrderDetailQuery,
                (rs, rowNum) -> new GetOrderDetailRes(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_detail_id"),
                        rs.getInt("order_detail_unit_price"),
                        rs.getInt("order_detail_quantity"),
                        rs.getInt("order_detail_discount"),
                        rs.getInt("order_detail_price"),
                        rs.getString("order_detail_status"),
                        rs.getInt("delivery_id")),
                getOrderDetailParams);
    }


    public int createOrderDetail(PostOrderDetailReq postOrderDetailReq) {
        String createOrderDetailQuery = "insert into coupang.ORDER_DETAIL (order_id, product_detail_id, order_detail_unit_price, order_detail_quantity, order_detail_discount, order_detail_price, order_detail_status, delivery_id) VALUES (?,?,?,?,?,?,?,?)";
        Object[] createOrderDetailParams = new Object[]{
                postOrderDetailReq.getOrder_id(),
                postOrderDetailReq.getProduct_detail_id(),
                postOrderDetailReq.getOrder_detail_unit_price(),
                postOrderDetailReq.getOrder_detail_quantity(),
                postOrderDetailReq.getOrder_detail_discount(),
                postOrderDetailReq.getOrder_detail_price(),
                postOrderDetailReq.getOrder_detail_status(),
                postOrderDetailReq.getDelivery_id()};
        this.jdbcTemplate.update(createOrderDetailQuery, createOrderDetailParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    ;


    public int modifyOrderDetailStatus(PatchOrderDetailReq patchOrderDetailReq) {
        String modifyOrderDetailStatusQuery = "update coupang.ORDER_DETAIL set order_detail_status = ? where id = ? ";
        Object[] modifyOrderDetailStatusParams = new Object[]{patchOrderDetailReq.getOrder_detail_status(),
                patchOrderDetailReq.getId()};

        return this.jdbcTemplate.update(modifyOrderDetailStatusQuery, modifyOrderDetailStatusParams);
    }

    public int deleteOrderDetail(int orderDetailId) {
        String deleteOrderDetailNameQuery = "update coupang.ORDER_DETAIL set status = ? where id = ? ";
        Object[] deleteOrderDetailNameParams = new Object[]{"INACTIVE", orderDetailId};

        return this.jdbcTemplate.update(deleteOrderDetailNameQuery, deleteOrderDetailNameParams);
    }


}
