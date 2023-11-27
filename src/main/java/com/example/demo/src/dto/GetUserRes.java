package com.example.demo.src.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int id;
    private String email;
    private String password;
    private String name;
}
