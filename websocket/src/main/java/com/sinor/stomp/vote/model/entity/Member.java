package com.sinor.stomp.vote.model.entity;

import com.sinor.stomp.vote.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Member implements BaseEntity<Long> {
    // top-level class
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // properties
    private String name;
    private String profile;

    @Version
    private Long version;

    @Builder
    public Member(String name, String profile) {
        this.name = name;
        this.profile = profile;
    }
}
