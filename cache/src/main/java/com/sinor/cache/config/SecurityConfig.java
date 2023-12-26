package com.sinor.cache.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		/**
		 * CSRF, CORS 설정
		 * 설명하자면 CSRF는 Cross Site Request Forgery의 약자로, 웹 사이트의 취약점을 이용해서 사용자가 의도하지 않은 요청을 통해 공격하는 것을 말한다.
		 * CORS는 Cross Origin Resource Sharing의 약자로, 웹 사이트에서 리소스를 공유할 때 발생하는 보안 문제를 해결하기 위한 방법이다.
		 * 이 두가지 설정을 disable 해주는 이유는, JWT 인증 처리를 하기 위해서는 CSRF, CORS 설정을 해제해야 한다.
		 * CSRF, CORS 설정을 해제하면, JWT 토큰을 받아서 인증 처리를 할 수 있다.
		 */
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(registry -> registry
				.requestMatchers("/admin/**").hasRole("admin")
				.anyRequest().permitAll()
			);
		/**
		 * OAuth 2.0와 JWT를 함께 사용하는 설정을 나타냅니다. JWT는 OAuth 2.0의 일부로 사용될 수 있으며, 많은 경우에는 OAuth 2.0 인증 서버에서 발급된 액세스 토큰이 JWT 형식을 갖습니다.
		 * 설명하자면 JWT 토큰을 받아서 인증을 처리하는데, 이때 JWT 토큰에는 realm_access라는 필드가 있는데 이 필드에는 사용자의 권한이 담겨있다.
		 * 이 필드를 이용해서 사용자의 권한을 인증 처리한다.
		 */
		httpSecurity
			.oauth2ResourceServer(oauth2Configurer -> oauth2Configurer.jwt(
				jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {
					Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
					Collection<String> roles = realmAccess.get("roles");
					var grantedAuthorities = roles.stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
						.toList();
					return new JwtAuthenticationToken(jwt, grantedAuthorities);
				})));
		/**
		 * 세션 관리
		 * JWT 토큰을 사용하기 때문에 세션을 사용하지 않는다.
		 * 세션을 사용하지 않기 때문에 세션 관리를 STATELESS로 설정한다.
		 */
		httpSecurity
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);
		return httpSecurity.build();
	}
}