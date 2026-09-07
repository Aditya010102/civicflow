package com.civicflow.advanced.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionDemo {

    public static void main(String[] args) {

        String url =
                "jdbc:postgresql://localhost:5432/civicflow";

        String username = "postgres";
        String password = "112";

        try {
            Connection connection =
                    DriverManager.getConnection(
                            url,
                            username,
                            password
                    );

            System.out.println("Database connected successfully.");

            connection.close();

        } catch (SQLException e) {

            System.out.println(
                    "Database connection failed."
            );

            e.printStackTrace();
        }
    }
}