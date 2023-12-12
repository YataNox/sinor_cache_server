package com.sinor.cache.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
	private ResponseStatus status;

	public CustomException(ResponseStatus status) {
		super(status.getMessage());
		fillInStackTrace();
		this.status = status;
	}

}
