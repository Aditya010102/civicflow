package com.civicflow.advanced.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcPreparedStatementDemo {

    public static void main(String[] args) {

        String url =
                "jdbc:postgresql://localhost:5432/civicflow";

        String username = "postgres";
        String password = "password";

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

            statement.setLong(1, 102);

            statement.setString(
                    2,
                    "Water leakage"
            );

            statement.setString(
                    3,
                    "Water leaking near the market"
            );

            statement.setString(
                    4,
                    "CRITICAL"
            );

            statement.setString(
                    5,
                    "REPORTED"
            );

            int rows =
                    statement.executeUpdate();

            System.out.println(
                    "Rows inserted: " + rows
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}