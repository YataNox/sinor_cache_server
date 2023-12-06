package com.sinor.cache.metadata;

import lombok.Builder;

@Builder
public class MetadataGetResponse {
	private String metadataUrl; // URL Key 값

	private Long metadataTtlSecond; // URL 별 설정 만료시간
}
