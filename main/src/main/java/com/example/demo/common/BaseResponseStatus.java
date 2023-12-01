package com.example.demo.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"중복된 이메일입니다."),

    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "already registered"),
    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"non matching"),


    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "DB Connection Failed"),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Connection Failed"),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저 삭제 실패");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
