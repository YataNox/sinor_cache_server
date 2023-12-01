package com.example.demo.src.user.repository;

import com.example.demo.src.user.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, String> {
    Optional<Option> findByUrl(String url);
}
