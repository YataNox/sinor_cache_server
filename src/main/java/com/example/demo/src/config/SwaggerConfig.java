package com.example.demo.src.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
