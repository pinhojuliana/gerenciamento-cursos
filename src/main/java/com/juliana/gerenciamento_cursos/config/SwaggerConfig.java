package com.juliana.gerenciamento_cursos.config;

import org.springframework.context.annotation.Bean;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.juliana.gerenciamento_cursos.controller")
                .build();
    }
}
