package com.sinor.cache.metadata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.cache.metadata.Metadata;

public interface MetadataRepository extends JpaRepository<Metadata, String> {

}
