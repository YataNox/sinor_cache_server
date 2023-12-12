package com.sinor.cache.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"timestamp", "isSuccess", "code", "message", "data"})
public class AdminSuccessResponse<T> {
	private final LocalDateTime timestamp;
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final int code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T data;

	// 요청에 성공한 경우
	private AdminSuccessResponse(ResponseStatus status, T data) {
		this.timestamp = LocalDateTime.now();
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.data = data;
	}

	private AdminSuccessResponse(ResponseStatus status) {
		this.timestamp = LocalDateTime.now();
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.data = null;
	}

	public static AdminSuccessResponse<?> fromNoData(ResponseStatus status){
		return new AdminSuccessResponse<>(status);
	}

	public static <T> AdminSuccessResponse<?> from(ResponseStatus status, T data){
		return new AdminSuccessResponse<>(status, data);
	}
}

