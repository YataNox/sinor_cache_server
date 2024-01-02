package com.sinor.cache.common.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainException extends RuntimeException {
	private MainResponseStatus status;
}
