package com.wcc.hospital.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WCC Hospital Patient API")
                        .description("Session 1 — Patient management: versioned REST, JWT auth, RFC 7807 error handling")
                        .version("2.0.0")
                        .contact(new Contact()
                                .name("WCC Mentorship 2026")
                                .url("https://github.com/HersiKopani/wcc-mentorship-2026")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Auth"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Auth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
