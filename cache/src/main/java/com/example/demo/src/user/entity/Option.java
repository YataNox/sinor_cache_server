package com.example.demo.src.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="options")
public class Option {
    // Increase Value를 캐시 Key로 활용할 수도 있지 않을까하는 마음에
    // URL을 키로 사용하던 것에서 Long 타입 id로 변경
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long expiredTime;
}
