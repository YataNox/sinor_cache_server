package com.sinor.cache.common;

import java.io.Serializable;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class CustomResponseEntity extends ResponseEntity<String> implements Serializable {

private static final long serialVersionUID = 7156526077883281625L;

	public CustomResponseEntity(HttpStatusCode status) {
		super(status);
	}

	public CustomResponseEntity(String body, HttpStatusCode status) {
		super(body, status);
	}

	public CustomResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
		super(headers, status);
	}

	public CustomResponseEntity(String body, MultiValueMap<String, String> headers, HttpStatusCode status) {
		super(body, headers, status);
	}

	public CustomResponseEntity(String body, MultiValueMap<String, String> headers, int rawStatus) {
		super(body, headers, rawStatus);
	}

	public CustomResponseEntity(ResponseEntity<String> responseEntity){
		super(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());
	}
}