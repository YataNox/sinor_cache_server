package com.sinor.cache.metadata;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.cache.metadata.Metadata;

public interface MetadataRepository extends JpaRepository<Metadata, String> {
}
