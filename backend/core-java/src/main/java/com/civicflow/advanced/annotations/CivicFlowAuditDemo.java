package com.civicflow.advanced.annotations;

public class CivicFlowAuditDemo {

    public static void main(String[] args) {

        CriticalOperationProcessor.process(
                AnnotationDemo.class
        );
    }
}