package com.civicflow.advanced.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcQueryDemo {

    public static void main(String[] args) {

        String url =
                "jdbc:postgresql://localhost:5432/civicflow";

        String username = "postgres";
        String password = "password";

        String sql = """
                SELECT id, title, description, priority, status
                FROM issues
                WHERE priority = ?
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

            statement.setString(1, "HIGH");

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    long id =
                            resultSet.getLong("id");

                    String title =
                            resultSet.getString("title");

                    String description =
                            resultSet.getString(
                                    "description"
                            );

                    String priority =
                            resultSet.getString("priority");

                    String status =
                            resultSet.getString("status");

                    System.out.println(
                            id + " | " +
                                    title + " | " +
                                    priority + " | " +
                                    status
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}