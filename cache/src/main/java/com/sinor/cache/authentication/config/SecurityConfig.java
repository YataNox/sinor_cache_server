package com.sinor.cache.authentication.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	/**
	 * @apiNote 인증서버에서 발급한 엑세스토큼을 이용하여 인증을 처리한다.
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(registry -> registry
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/v**/authentication").permitAll()
				.anyRequest().permitAll())
			.oauth2ResourceServer(oauth2ResourceServer ->
				oauth2ResourceServer.jwt(jwt ->
					jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
			.sessionManagement(sessionManagementConfigurer ->
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	/**
	 * @apiNote jwt를 이용하여 인증을 처리한다.
	 * @apiNote jwt의 roles를 이용하여 Admin권한을 검색하여 ADMIN이 있다면 admin권한을 부여한다.
	 * @return
	 */
	private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {

		return jwt -> {
			Collection<String> authorities = (Collection<String>)jwt.getClaims().get("roles");
			List<GrantedAuthority> grantedAuthorities = authorities.stream()
				.map(authority -> "ROLE_" + authority.toUpperCase())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
			return new JwtAuthenticationToken(jwt, grantedAuthorities);
		};
	}
}
