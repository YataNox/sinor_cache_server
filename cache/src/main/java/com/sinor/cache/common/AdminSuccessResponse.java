package com.sinor.cache.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
	public AdminSuccessResponse(ResponseStatus status, T data) {
		this.timestamp = LocalDateTime.now();
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.data = data;
	}

	public static AdminSuccessResponse<?> fromNoData(ResponseStatus status){
		return AdminSuccessResponse.builder()
			.timestamp(LocalDateTime.now())
			.isSuccess(status.isSuccess())
			.code(status.getCode())
			.message(status.getMessage())
			.build();
	}

	public static <T> AdminSuccessResponse<?> fromData(ResponseStatus status, T data){
		return AdminSuccessResponse.builder()
			.timestamp(LocalDateTime.now())
			.isSuccess(status.isSuccess())
			.code(status.getCode())
			.message(status.getMessage())
			.data(data)
			.build();
	}
}

