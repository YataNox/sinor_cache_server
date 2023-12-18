package com.sinor.cache.common;

import java.io.Serializable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomResponse implements Serializable {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String body;
	private HttpHeaders headers;
	private int statusCodeValue;
	public static CustomResponse from(ResponseEntity<String> entity){
		return CustomResponse.builder()
			.body(entity.getBody())
			.headers(entity.getHeaders())
			.statusCodeValue(entity.getStatusCode().value())
			.build();
	}
}
