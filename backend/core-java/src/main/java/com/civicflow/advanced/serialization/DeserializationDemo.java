package com.civicflow.advanced.serialization;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class DeserializationDemo {

    public static void main(String[] args) throws Exception {

        String json = """
                {
                    "id": 101,
                    "title": "Broken streetlight",
                    "priority": "HIGH"
                }
                """;

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> issue =
                mapper.readValue(json, Map.class);

        System.out.println(issue);

        System.out.println(issue.get("title"));
    }
}