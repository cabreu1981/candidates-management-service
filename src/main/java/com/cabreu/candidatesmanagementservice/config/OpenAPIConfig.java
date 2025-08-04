package com.cabreu.candidatesmanagementservice.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(
            new Info()
                .title("Candidates Management API for SEEK")
                .version("1.0.0")
                .description("API para gestionar candidatos y autenticación")
        );
    }
}
