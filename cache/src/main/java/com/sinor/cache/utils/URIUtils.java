package com.sinor.cache.utils;

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

	public static String getUriPathQuery(String path, MultiValueMap<String, String> queryParams, int version) {
		UriComponentsBuilder uriComponents = UriComponentsBuilder.fromPath(path);

		if (queryParams != null)
			uriComponents.queryParams(queryParams);

		System.out.println(uriComponents.toUriString());
		return uriComponents.toUriString() + "/V" + version;
	}
}
