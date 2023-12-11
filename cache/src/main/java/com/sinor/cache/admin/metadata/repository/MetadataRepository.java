package com.sinor.cache.admin.metadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.cache.admin.metadata.Metadata;

public interface MetadataRepository extends JpaRepository<Metadata, String> {

}
