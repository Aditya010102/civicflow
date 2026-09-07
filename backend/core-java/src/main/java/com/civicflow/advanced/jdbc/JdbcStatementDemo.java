package com.civicflow.advanced.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcStatementDemo {

    public static void main(String[] args) {

        String url =
                "jdbc:postgresql://localhost:5432/civicflow";

        String username = "postgres";
        String password = "112";

        String sql = """
                INSERT INTO issues
                (id, title, description, priority, status)
                VALUES
                (101,
                 'Broken streetlight',
                 'Streetlight not working',
                 'HIGH',
                 'REPORTED')
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(
                                url,
                                username,
                                password
                        );

                Statement statement =
                        connection.createStatement()
        ) {

            int rows =
                    statement.executeUpdate(sql);

            System.out.println(
                    "Rows inserted: " + rows
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}