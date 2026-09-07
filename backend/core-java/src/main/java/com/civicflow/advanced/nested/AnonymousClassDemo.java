package com.civicflow.advanced.nested;

public class AnonymousClassDemo {

    interface IssueProcessor {
        void process();
    }

    public static void main(String[] args) {

        IssueProcessor processor =
                new IssueProcessor() {

                    @Override
                    public void process() {
                        System.out.println(
                                "Processing civic issue..."
                        );
                    }
                };

        processor.process();
    }
}