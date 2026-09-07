package com.civicflow.advanced.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTransactionDemo {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/civicflow";

    private static final String USER = "postgres";

    private static final String PASSWORD = "112";

    public static void main(String[] args) {

        String insertIssue = """
                INSERT INTO issues
                (id, title, description, priority, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        String insertAudit = """
                INSERT INTO issue_audit_logs
                (issue_id, action, created_at)
                VALUES (?, ?, CURRENT_TIMESTAMP)
                """;

        try (Connection connection =
                     DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement issueStatement =
                     connection.prepareStatement(insertIssue);

             PreparedStatement auditStatement =
                     connection.prepareStatement(insertAudit)) {

            connection.setAutoCommit(false);

            try {

                issueStatement.setLong(1, 101);
                issueStatement.setString(
                        2,
                        "Broken streetlight"
                );
                issueStatement.setString(
                        3,
                        "Streetlight not working near park"
                );
                issueStatement.setString(
                        4,
                        "HIGH"
                );
                issueStatement.setString(
                        5,
                        "REPORTED"
                );

                issueStatement.executeUpdate();

                auditStatement.setLong(1, 101);
                auditStatement.setString(
                        2,
                        "ISSUE_CREATED"
                );

                auditStatement.executeUpdate();

                connection.commit();

                System.out.println(
                        "Transaction committed successfully."
                );

            } catch (Exception e) {

                connection.rollback();

                System.out.println(
                        "Transaction rolled back."
                );

                e.printStackTrace();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}