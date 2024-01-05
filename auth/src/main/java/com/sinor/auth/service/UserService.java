package com.sinor.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.auth.domain.Role;
import com.sinor.auth.domain.User;
import com.sinor.auth.dto.AuthDto;
import com.sinor.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void registerUser(AuthDto.SignupDto signupDto) {
		String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
		User user = User.registerUser(signupDto.getEmail(), encodedPassword, Role.ADMIN);
		userRepository.save(user);
	}
}
