package com.sinor.cache.option.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.cache.option.Option;

public interface OptionRepository extends JpaRepository<Option, String> {
	Optional<Option> findByUrl(String url);
}
