package com.sinor.cache.admin.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metadata")
public class Metadata {
	@Id
	private String metadataUrl;

	@Column(nullable = false)
	private Long metadataTtlSecond;
}
