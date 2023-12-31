package com.sinor.auth.OAuth2.authorizationCode;

import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(
		HttpSecurity http,
		UserDetailsService userDetailsService,
		JwtTokenService jwtTokenService
	) throws Exception {
		return http
			.authorizeHttpRequests(authorize -> {
				authorize.requestMatchers("/api/v**/authentication").permitAll();
				authorize.requestMatchers("/api/v**/refresh").permitAll();
				authorize.anyRequest().authenticated();
			})
			.sessionManagement(session -> {
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})

			.httpBasic(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtTokenService),
				UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("sinor")
			.clientSecret(passwordEncoder.encode("sinor-q5n2g"))
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
			.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(60)).build())
			.build();
		return new InMemoryRegisteredClientRepository(client);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		User.UserBuilder users = User.builder();
		UserDetails user = users
			.username("sinor")
			.password(passwordEncoder.encode("sinor-q5n2g"))
			.roles("ADMIN")
			.build();
		return new InMemoryUserDetailsManager(user);
	}

}
