package com.civicflow.advanced.nested;

public class InnerClassDemo {

    private long issueId;

    public InnerClassDemo(long issueId) {
        this.issueId = issueId;
    }

    class AssignmentInfo {

        private long officerId;

        public AssignmentInfo(long officerId) {
            this.officerId = officerId;
        }

        public void printAssignment() {
            System.out.println(
                    "Issue ID: " + issueId
                            + ", Officer ID: " + officerId
            );
        }
    }

    public static void main(String[] args) {

        InnerClassDemo issue =
                new InnerClassDemo(101);

        InnerClassDemo.AssignmentInfo assignment =
                issue.new AssignmentInfo(5001);

        assignment.printAssignment();
    }
}