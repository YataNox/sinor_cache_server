package com.example.demo.src.jdbctemplate.orderdetail;

import static com.example.demo.common.BaseResponseStatus.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.common.BaseException;
import com.example.demo.src.jdbctemplate.orderdetail.model.GetOrderDetailRes;
import com.example.demo.src.jdbctemplate.orderdetail.model.PatchOrderDetailReq;
import com.example.demo.src.jdbctemplate.orderdetail.model.PostOrderDetailReq;
import com.example.demo.src.jdbctemplate.orderdetail.model.PostOrderDetailRes;

import lombok.RequiredArgsConstructor;

// Service Create, Update, Delete 의 로직 처리
@RequiredArgsConstructor
@Service
public class OrderDetailService {

	private final OrderDetailDao orderDetailDao;

	//POST
	public PostOrderDetailRes createOrderDetail(PostOrderDetailReq postOrderDetailReq) throws BaseException {

		try {
			int orderDetailId = orderDetailDao.createOrderDetail(postOrderDetailReq); // POINT
			return new PostOrderDetailRes(orderDetailId);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public void modifyOrderDetailName(PatchOrderDetailReq patchOrderDetailReq) throws BaseException {
		try {
			int result = orderDetailDao.modifyOrderDetailStatus(patchOrderDetailReq);
			if (result == 0) {
				throw new BaseException(MODIFY_FAIL_USERNAME);
			}
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public void deleteOrderDetail(int orderDetailId) throws BaseException {
		try {
			int result = orderDetailDao.deleteOrderDetail(orderDetailId);
			if (result == 0) {
				throw new BaseException(DELETE_FAIL_USERNAME);
			}
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<GetOrderDetailRes> getOrderDetails() throws BaseException {
		try {
			List<GetOrderDetailRes> getOrderDetailRes = orderDetailDao.getOrderDetails();
			return getOrderDetailRes;
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<GetOrderDetailRes> getOrderDetailsByOrderId(Integer orderId) throws BaseException {
		try {
			List<GetOrderDetailRes> getOrderDetailsRes = orderDetailDao.getOrderDetailsByOrderId(orderId);
			return getOrderDetailsRes;
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public GetOrderDetailRes getOrderDetail(int orderDetailId) throws BaseException {
		try {
			GetOrderDetailRes getOrderDetailRes = orderDetailDao.getOrderDetail(orderDetailId);
			return getOrderDetailRes;
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

}
