package com.sinor.cache.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
	info = @Info(title = "API Swagger Test",
		description = "Test",
		version = "v1")
)

public class SwaggerConfig {
	@Bean
	public GroupedOpenApi groupedOpenApi() {
		String[] paths = {"/app/**"};

		return GroupedOpenApi.builder()
			.group("Api Test")
			.pathsToMatch(paths)
			.build();
	}
}
