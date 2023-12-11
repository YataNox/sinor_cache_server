package com.sinor.cache.metadata.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MetadataGetResponse {
	private String metadataUrl; // URL Key 값

	private Long metadataTtlSecond; // URL 별 설정 만료시간
}
