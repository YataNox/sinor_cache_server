package com.sinor.cache.admin.metadata.model;

import com.sinor.cache.admin.metadata.Metadata;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MetadataGetResponse {
	private String metadataUrl; // URL Key 값

	private Long metadataTtlSecond; // URL 별 설정 만료시간

	private int version; // 수정 버전

	public static MetadataGetResponse from(Metadata metadata){
		return MetadataGetResponse.builder()
			.metadataUrl(metadata.getMetadataUrl())
			.metadataTtlSecond(metadata.getMetadataTtlSecond())
			.version(metadata.getVersion())
			.build();
	}
}
