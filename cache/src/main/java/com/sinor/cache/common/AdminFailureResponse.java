package com.sinor.cache.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"timestamp", "isSuccess", "code", "message"})
public class AdminFailureResponse {
	private final LocalDateTime timestamp;
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final int code;
	private final String message;

	private AdminFailureResponse(ResponseStatus status) {
		this.timestamp = LocalDateTime.now();
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
	}

	public static AdminFailureResponse from(ResponseStatus status){
		return new AdminFailureResponse(status);
	}
}
