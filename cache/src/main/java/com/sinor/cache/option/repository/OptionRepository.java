package com.sinor.cache.option.repository;


import com.sinor.cache.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, String> {
    Optional<Option> findByUrl(String url);
}
