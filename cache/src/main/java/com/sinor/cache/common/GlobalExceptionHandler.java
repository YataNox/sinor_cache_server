package com.sinor.cache.common;

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
		log.error("Custom Exception Occurred : " + e.getStatus().getCode(), e);

		AdminFailureResponse responseBody = AdminFailureResponse.from(e.getStatus());

		return ResponseEntity.status((e).getStatus().getCode()).body(responseBody);
	}

	/**
	 * Internal Server Error Exception Handler
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> Exception(Exception e){
		log.error("Internal Server Error occurred : " + HttpStatus.INTERNAL_SERVER_ERROR.value(), e);

		AdminFailureResponse responseBody = AdminFailureResponse.from(com.sinor.cache.common.ResponseStatus.INTERNAL_SERVER_ERROR);

		return ResponseEntity.internalServerError().body(responseBody);
	}
}