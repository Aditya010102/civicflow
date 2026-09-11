package com.civicflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI civicFlowOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("CivicFlow API")
                                .version("1.0.0")
                                .description(
                                        "REST API for managing "
                                                + "community civic issues."
                                )
                                .contact(
                                        new Contact()
                                                .name("CivicFlow Team")
                                )
                );
    }
}