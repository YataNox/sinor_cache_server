package com.example.demo.src.jdbctemplate.orderdetail;

import com.example.demo.common.BaseException;
import com.example.demo.common.BaseResponse;
import com.example.demo.src.jdbctemplate.orderdetail.model.GetOrderDetailRes;
import com.example.demo.src.jdbctemplate.orderdetail.model.OrderDetail;
import com.example.demo.src.jdbctemplate.orderdetail.model.PatchOrderDetailReq;
import com.example.demo.src.jdbctemplate.orderdetail.model.PostOrderDetailReq;
import com.example.demo.src.jdbctemplate.orderdetail.model.PostOrderDetailRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/orderdetails")
public class OrderDetailController {


    private final OrderDetailService orderDetailService;


    /**
     * 상품 상세 추가 API [POST] /app/orderdetails
     *
     * @return BaseResponse<PostOrderDetailRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostOrderDetailRes> createOrderDetail(@RequestBody PostOrderDetailReq postOrderDetailReq) {
        try {
            PostOrderDetailRes postOrderDetailRes = orderDetailService.createOrderDetail(postOrderDetailReq);
            return new BaseResponse<>(postOrderDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 상세 조회 API [GET] /app/orderdetails 상품 상세 번호 검색 조회 API [GET] /app/orderdetails? orderId=
     *
     * @return BaseResponse<List < GetOrderDetailRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:8080/app/orderdetails
    public BaseResponse<List<GetOrderDetailRes>> getOrderDetails(@RequestParam(required = false) Integer orderId) {
        try {
            if (orderId == null) {
                List<GetOrderDetailRes> getOrderDetailsRes = orderDetailService.getOrderDetails();
                return new BaseResponse<>(getOrderDetailsRes);
            }
            // Get OrderDetails
            List<GetOrderDetailRes> getOrderDetailsRes = orderDetailService.getOrderDetailsByOrderId(orderId);
            return new BaseResponse<>(getOrderDetailsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 상세 1개 조회 API [GET] /app/orderdetails/:orderdetailId
     *
     * @return BaseResponse<GetOrderDetailRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{orderDetailId}") // (GET) 127.0.0.1:9000/app/orderdetails/:orderdetailId
    public BaseResponse<GetOrderDetailRes> getOrderDetail(@PathVariable("orderDetailId") int orderDetailId) {
        // Get OrderDetails
        try {
            GetOrderDetailRes getOrderDetailRes = orderDetailService.getOrderDetail(orderDetailId);
            return new BaseResponse<>(getOrderDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 유저정보변경 API [PATCH] /app/orderdetails/:orderdetailId
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{orderdetailId}")
    public BaseResponse<String> modifyOrderDetailName(@PathVariable("orderdetailId") int orderdetailId,
                                                      @RequestBody OrderDetail orderDetail) {
        try {
            PatchOrderDetailReq patchOrderDetailReq = new PatchOrderDetailReq(orderdetailId,
                    orderDetail.getOrder_detail_status());
            orderDetailService.modifyOrderDetailName(patchOrderDetailReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저정보삭제 API [DELETE] /app/orderdetails/:orderdetailId
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/{orderDetailId}")
    public BaseResponse<String> deleteOrderDetail(@PathVariable("orderDetailId") int orderDetailId) {
        try {
            orderDetailService.deleteOrderDetail(orderDetailId);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
