package com.sinor.cache.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class URIUtils {

	public static UriComponentsBuilder uriComponentsBuilder(String path, MultiValueMap<String, String> queryParams) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://mainHost:8080/");
		builder.path(path);

		if (queryParams != null)
			builder.queryParams(queryParams);

		System.out.println("builder : " + builder.toUriString());
		return builder;
	}

	public static String getResponseKey(String path, MultiValueMap<String, String> queryParams, int version) {
		UriComponentsBuilder uriComponents = UriComponentsBuilder.fromPath(path);

		if (queryParams != null)
			uriComponents.queryParams(queryParams);

		System.out.println(uriComponents.toUriString());
		return uriComponents.toUriString() + "/V" + version;
	}

	/**
	 * url에 포함되어있는 한글 등을 인코딩
	 * @param queryParams 요청에 전달될 값
	 * @return 인코딩되 결과값
	 */
	public static MultiValueMap<String, String> encodingUrl(MultiValueMap<String, String> queryParams) {

		MultiValueMap<String, String> encodedQueryParams = new LinkedMultiValueMap<>();

		for (String key : queryParams.keySet()) {
			List<String> encodedValues = queryParams.get(key).stream()
				.map(value -> {
					try {
						return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());
			encodedQueryParams.put(key, encodedValues);
		}

		return encodedQueryParams;
	}
}
