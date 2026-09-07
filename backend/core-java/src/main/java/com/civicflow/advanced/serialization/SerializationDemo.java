package com.civicflow.advanced.serialization;
import tools.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class SerializationDemo {

    public static void main(String[] args) throws Exception {

        Map<String, Object> issue = new HashMap<>();

        issue.put("id", 101);
        issue.put("title", "Broken streetlight");
        issue.put("priority", "HIGH");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(issue);

        System.out.println(json);
    }
}