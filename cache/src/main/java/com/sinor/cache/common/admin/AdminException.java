package com.sinor.cache.common.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminException extends RuntimeException {
	private AdminResponseStatus status;

}
