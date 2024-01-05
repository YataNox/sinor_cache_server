package com.sinor.auth.domain;

import com.sinor.auth.dto.AuthDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String email; // Principal

	private String password; // Credential

	@Enumerated(EnumType.STRING)
	private Role role; // 사용자 권한

	// == 생성 메서드 == //
	public static User registerUser(String email, String encodedPassword, Role role) {
		User user = new User();
		user.email = email;
		user.password = encodedPassword;
		user.role = role;
		return user;
	}
}