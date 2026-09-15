package com.civicflow.client;

import com.civicflow.client.dto.DepartmentClientResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DepartmentClient {

    private final RestClient restClient;

    public DepartmentClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public DepartmentClientResponse getDepartment(Long id) {

        return restClient
                .get()
                .uri("/api/departments/{id}", id)
                .retrieve()
                .body(DepartmentClientResponse.class);
    }
}