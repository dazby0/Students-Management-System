package com.example.student_management_system.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the database connection.
 * Handles connecting to and closing the SQLite database.
 */
public class DatabaseConnection {

    // Connection URL for the SQLite database
    private static final String URL = "jdbc:sqlite:students.db";

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A {@link Connection} object to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
