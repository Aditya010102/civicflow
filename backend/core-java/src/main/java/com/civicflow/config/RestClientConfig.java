package com.civicflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient departmentRestClient(
            @Value("${civicflow.services.department.base-url}")
            String departmentBaseUrl
    ) {
        return RestClient.builder()
                .baseUrl(departmentBaseUrl)
                .build();
    }
}