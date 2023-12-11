package com.sinor.cache.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class BaseResponse<T> {
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final int code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	// 요청에 성공한 경우
	public BaseResponse(BaseResponseStatus status, T data) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.data = data;
	}

	// 요청에 실패한 경우
	public BaseResponse(BaseResponseStatus status) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
	}

}

