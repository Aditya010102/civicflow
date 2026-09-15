package com.civicflow.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(
        DepartmentServiceProperties.class
)
public class RestClientConfig {

    @Bean
    public RestClient departmentRestClient(
            DepartmentServiceProperties properties
    ) {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}