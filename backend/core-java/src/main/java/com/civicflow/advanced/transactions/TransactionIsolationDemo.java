package com.civicflow.advanced.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionIsolationDemo {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/civicflow";

    private static final String USER = "postgres";

    private static final String PASSWORD = "112";

    public static void main(String[] args) {

        try (Connection connection =
                     DriverManager.getConnection(
                             URL,
                             USER,
                             PASSWORD
                     )) {

            System.out.println(
                    "Current isolation level: "
                            + connection.getTransactionIsolation()
            );

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED
            );

            System.out.println(
                    "Isolation level updated."
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}