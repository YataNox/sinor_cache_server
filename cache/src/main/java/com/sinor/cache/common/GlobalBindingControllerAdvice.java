package com.sinor.cache.common;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalBindingControllerAdvice {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> customException(CustomException e){
		log.error("Base exception occurred" + e.getStatus().getCode(), e);

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("timestamp", LocalDateTime.now());
		responseBody.put("isSuccess", e.getStatus().isSuccess());
		responseBody.put("status", e.getStatus().getCode());
		responseBody.put("message", e.getStatus().getMessage());

		return ResponseEntity.status((e).getStatus().getCode()).body(responseBody);
	}
}