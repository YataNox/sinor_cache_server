package com.example.demo.src.user.repository;

import com.example.demo.src.user.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, String> {
}
