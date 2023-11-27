package com.example.demo.src.user.repository;

import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);


}

