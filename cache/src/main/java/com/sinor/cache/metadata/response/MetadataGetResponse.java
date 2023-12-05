package com.sinor.cache.metadata.response;

import lombok.Builder;

@Builder
public class MetadataGetResponse {
	private String metadataIdB64Path; // URL Key 값

	private int metadataTtlSecond; // URL 별 설정 만료시간

	private int metadataActiveAmount; // 활성화 중인 하위 URL 수
}
