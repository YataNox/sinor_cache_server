package com.sinor.cache.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"timestamp", "isSuccess", "code", "message", "data"})
public class AdminResponse<T> {
	private final LocalDateTime timestamp;
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final int code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	// 요청에 성공한 경우
	public AdminResponse(ResponseStatus status, T data) {
		this.timestamp = LocalDateTime.now();
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.data = data;
	}

	// 요청에 실패한 경우 or 반환 값이 없는 경우
	public AdminResponse(ResponseStatus status) {
		this.timestamp = LocalDateTime.now();
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
	}

}

