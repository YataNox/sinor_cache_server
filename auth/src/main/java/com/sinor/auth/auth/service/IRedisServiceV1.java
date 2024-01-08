package com.sinor.auth.auth.service;

import org.springframework.transaction.annotation.Transactional;

public interface IRedisServiceV1 {

	@Transactional
	void setValues(String key, String value);

	// 만료시간 설정 -> 자동 삭제
	@Transactional
	void setValuesWithTimeout(String key, String value, long timeout);

	String getValues(String key);

	@Transactional
	void deleteValues(String key);

}
