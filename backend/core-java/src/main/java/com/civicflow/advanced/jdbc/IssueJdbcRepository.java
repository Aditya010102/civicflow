package com.civicflow.advanced.jdbc;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueJdbcRepository {

    private final String url =
            "jdbc:postgresql://localhost:5432/civicflow";

    private final String username = "postgres";

    private final String password = "password";

    public void save(Issue issue) {

        String sql = """
                INSERT INTO issues
                (id, title, description, priority, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    issue.getId()
            );

            statement.setString(
                    2,
                    issue.getTitle()
            );

            statement.setString(
                    3,
                    issue.getDescription()
            );

            statement.setString(
                    4,
                    issue.getPriority().name()
            );

            statement.setString(
                    5,
                    issue.getStatus().name()
            );

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to save issue",
                    e
            );
        }
    }

    public List<Issue> findAll() {

        String sql = """
                SELECT id, title, description, priority, status
                FROM issues
                """;

        List<Issue> issues =
                new ArrayList<>();

        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Issue issue = new Issue(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        IssuePriority.valueOf(
                                resultSet.getString("priority")
                        ),
                        IssueStatus.valueOf(
                                resultSet.getString("status")
                        )
                );

                issues.add(issue);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to retrieve issues",
                    e
            );
        }

        return issues;
    }
}