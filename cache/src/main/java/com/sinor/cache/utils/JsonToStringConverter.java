package com.sinor.cache.utils;

import static com.sinor.cache.common.ResponseStatus.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinor.cache.common.CustomException;

public class JsonToStringConverter {
	private final ObjectMapper objectMapper;

	public JsonToStringConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	// 역직렬화 Redis value를 ApiGetReponse로 변환
	public <T> T jsontoClass(String jsonValue, Class<T> clazz) throws CustomException {
		try {
			return objectMapper.readValue(jsonValue, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException(DESERIALIZATION_ERROR);
		}
	}

	// 직렬화
	public <T> String objectToJson(T jsonValue) throws CustomException {
		try {
			return objectMapper.writeValueAsString(jsonValue);
		} catch (JsonProcessingException e) {
			throw new CustomException(DESERIALIZATION_ERROR);
		}
	}
}
