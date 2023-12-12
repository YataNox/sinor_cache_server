package com.sinor.cache.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum ResponseStatus {
	/**
	 * 2xx : 요청 성공
	 */
	SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),
	CREATED(true, HttpStatus.CREATED.value(), "새로운 리소스가 생성되었습니다."),
	ACCEPTED(true, HttpStatus.ACCEPTED.value(), "처리가 완료되지 않았습니다."),
	NO_CONTENT(true, HttpStatus.NO_CONTENT.value(), "요청에 성공하였습니다."),

	/**
	 * 4xx : Request, Response 오류
	 */
	BAD_REQUEST(false, HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
	UNAUTHORIZED(false, HttpStatus.UNAUTHORIZED.value(), "액세스 권한이 없습니다."),
	NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "리소스를 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED(false, HttpStatus.METHOD_NOT_ALLOWED.value(), "허용되지 않은 메소드"),

	/**
	 * 5xx :  Database, Server 오류
	 */
	API_CACHE_NOT_FOUND(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "해당 Cache를 찾지 못했습니다."),
	JSON_PROCESSING_EXCEPTION(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "직렬화/역직렬화에 실패했습니다."),
	METADATA_NOT_FOUND(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "해당 Metadata를 찾지 못했습니다."),
	INTERNAL_SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부의 서버 오류가 발생했습니다."),
	NOT_IMPLEMENTED(false, HttpStatus.NOT_IMPLEMENTED.value(), "구현되지 않은 요청입니다."),
	BAD_GATEWAY(false, HttpStatus.BAD_GATEWAY.value(), "잘못된 게이트웨이");


	private final boolean isSuccess;
	private final int code;
	private final String message;

	ResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}
