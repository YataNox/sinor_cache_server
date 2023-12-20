package com.sinor.cache.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeyCloakConfig {
	/**
	 * Keycloak Admin Client Bean
	 * @return
	 */
	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
			.serverUrl("http://keycloakHost:8443")
			.realm("sinor")
			.clientId("sinor-client")
			.username("sinoradmin")
			.password("sinor-q5n2g")
			.grantType("password")
			.build();
	}
}
