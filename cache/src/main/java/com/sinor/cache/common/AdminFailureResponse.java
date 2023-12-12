package com.sinor.cache.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonPropertyOrder({"timestamp", "isSuccess", "code", "message"})
public class AdminFailureResponse {
	private final LocalDateTime timestamp;
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final int code;
	private final String message;

	public static AdminFailureResponse from(ResponseStatus status){
		return AdminFailureResponse.builder()
			.timestamp(LocalDateTime.now())
			.isSuccess(status.isSuccess())
			.code(status.getCode())
			.message(status.getMessage())
			.build();
	}
}
