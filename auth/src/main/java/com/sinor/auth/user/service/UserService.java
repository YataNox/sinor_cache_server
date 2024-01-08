package com.sinor.auth.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinor.auth.auth.dto.AuthDto;
import com.sinor.auth.user.domain.Role;
import com.sinor.auth.user.domain.User;
import com.sinor.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserServiceV1 {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void registerUser(AuthDto.SignupDto signupDto) {
		String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
		User user = User.registerUser(signupDto.getEmail(), encodedPassword, Role.ADMIN);
		userRepository.save(user);
	}
}
