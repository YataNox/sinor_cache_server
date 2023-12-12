package com.sinor.cache.common;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * CustomException Handler
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> customException(CustomException e){
		log.error("Base exception occurred : " + e.getStatus().getCode(), e);

		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp", LocalDateTime.now());
		responseBody.put("isSuccess", e.getStatus().isSuccess());
		responseBody.put("status", e.getStatus().getCode());
		responseBody.put("message", e.getStatus().getMessage());

		return ResponseEntity.status((e).getStatus().getCode()).body(responseBody);
	}

	/**
	 * Internal Server Error Exception Handler
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> Exception(Exception e){
		log.error("Internal Server Error occurred : " + HttpStatus.INTERNAL_SERVER_ERROR.value(), e);

		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("timestamp", LocalDateTime.now());
		responseBody.put("isSuccess", false);
		responseBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseBody.put("message", "Internal Server Error occurred");

		return ResponseEntity.internalServerError().body(responseBody);
	}
}