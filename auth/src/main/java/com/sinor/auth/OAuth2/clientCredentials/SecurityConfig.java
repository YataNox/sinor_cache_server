// package com.sinor.auth.OAuth2.clientCredentials;
//
// import java.nio.charset.StandardCharsets;
// import java.security.KeyPair;
// import java.security.KeyPairGenerator;
// import java.security.NoSuchAlgorithmException;
// import java.security.SecureRandom;
// import java.security.interfaces.RSAPrivateKey;
// import java.security.interfaces.RSAPublicKey;
// import java.time.Duration;
// import java.util.Collections;
// import java.util.List;
// import java.util.Set;
// import java.util.UUID;
// import java.util.stream.Collectors;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.oauth2.core.AuthorizationGrantType;
// import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
// import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
// import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
// import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
// import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
// import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
// import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
// import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
// import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
// import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
// import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
// import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
//
// import com.nimbusds.jose.jwk.JWKSet;
// import com.nimbusds.jose.jwk.RSAKey;
// import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
// import com.nimbusds.jose.jwk.source.JWKSource;
// import com.nimbusds.jose.proc.SecurityContext;
//
// @Configuration
// @EnableWebSecurity(debug = true)
// public class SecurityConfig {
//
// 	@Bean
// 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// 		http
// 			.authorizeHttpRequests((authorize) -> {
// 				authorize.anyRequest().permitAll();
// 			})
// 			.oauth2ResourceServer((oauth2) -> {
// 				oauth2.jwt(Customizer.withDefaults());
// 			})
// 			.csrf(AbstractHttpConfigurer::disable);
// 		// .apply(new OAuth2AuthorizationServerConfigurer());
// 		return http.build();
// 	}
//
// 	@Bean
// 	public AuthorizationServerSettings authorizationServerSettings() {
// 		return AuthorizationServerSettings.builder()
// 			.tokenEndpoint("/v1/oauth/token") // default : /oauth/token
// 			.build();
// 	}
//
// 	@Bean
// 	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
// 		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
// 			.clientId("sinor")
// 			.clientSecret(passwordEncoder.encode("sinor-q5n2g"))
// 			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
// 			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
// 			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//
// 			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
// 			.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofSeconds(60)).build())
// 			.build();
// 		return new InMemoryRegisteredClientRepository(client);
// 	}
//
// 	@Bean
// 	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
// 		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
// 		jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(List.of(
// 			new JwtTimestampValidator(Duration.ofSeconds(0))
// 			// 디폴트 세팅으로는 expire 체크시 60초의 유예시간을 두고 체크하고 있는데, 유예시간을 제거하기 위한 설정
// 		)));
// 		return jwtDecoder;
// 	}
//
// 	@Bean
// 	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
// 		return new NimbusJwtEncoder(jwkSource);
// 	}
//
// 	@Bean
// 	public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
// 		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
// 		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
// 		RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
// 		return new ImmutableJWKSet<>(new JWKSet(rsaKey));
// 	}
//
// 	@Bean
// 	public KeyPair keyPair() throws NoSuchAlgorithmException {
// 		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
// 		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
// 		secureRandom.setSeed("test-secure-seed-v1".getBytes(
// 			StandardCharsets.UTF_8)); // 서버 재실행할 경우 이전에 발급했던 Jwt 토큰을 계속 사용할 수 있도록 고정된 시드값 설정.
// 		keyPairGenerator.initialize(2048, secureRandom);
// 		return keyPairGenerator.generateKeyPair();
// 	}
//
// 	@Bean
// 	public UserDetailsService users() {
// 		UserDetails user = User.builder()
// 			.username("user")
// 			.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
// 			.roles("USER")
// 			.build();
// 		UserDetails admin = User.builder()
// 			.username("admin")
// 			.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
// 			.roles("USER", "ADMIN")
// 			.build();
// 		return new InMemoryUserDetailsManager(user, admin);
// 	}
//
// 	@Bean
// 	public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
// 		return (context) -> {
// 			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
// 				context.getClaims().claims((claims) -> {
// 					Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
// 						.stream()
// 						.map(c -> c.replaceFirst("^ROLE_", ""))
// 						.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
// 					claims.put("roles", roles);
// 				});
// 			}
// 		};
// 	}
//
// 	@Bean
// 	public PasswordEncoder passwordEncoder() {
// 		return new BCryptPasswordEncoder();
// 	}
// }