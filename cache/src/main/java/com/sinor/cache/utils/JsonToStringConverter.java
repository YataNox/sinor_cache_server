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

	public <T> T jsonToString(String jsonValue, Class<T> clazz) throws CustomException {
		try {
			return objectMapper.readValue(jsonValue, clazz);
		} catch (JsonProcessingException e) {
			throw new CustomException(DESERIALIZATION_ERROR);
		}
	}

	public <T> String objectToJson(T jsonValue) throws CustomException {
		try {
			return objectMapper.writeValueAsString(jsonValue);
		} catch (JsonProcessingException e) {
			throw new CustomException(DESERIALIZATION_ERROR);
		}
	}
}
