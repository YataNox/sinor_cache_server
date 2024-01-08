package com.sinor.auth.user.details;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sinor.auth.user.domain.User;
import com.sinor.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
		User findUser = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Can't find user with this email. -> " + email));

		if (findUser != null) {
			System.out.println("findUser가 있음 : " + findUser);
			return new UserDetailsImpl(findUser);
		}
		return null;
	}
}
