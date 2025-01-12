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
    private static String URL = "jdbc:sqlite:students.db";

    // Mock connection for testing
    private static Connection mockConnection = null;

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A {@link Connection} object to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection connect() throws SQLException {
        if (mockConnection != null) {
            return mockConnection;
        }
        return DriverManager.getConnection(URL);
    }

    /**
     * Sets a custom URL for the database connection.
     * This method is useful for testing or dynamic configuration.
     *
     * @param customUrl The custom database URL to set.
     */
    public static void setUrl(String customUrl) {
        URL = customUrl;
    }

    /**
     * Sets a mock connection for testing.
     *
     * @param connection The mock {@link Connection} to use.
     */
    public static void setMockConnection(Connection connection) {
        mockConnection = connection;
    }

    /**
     * Resets the mock connection (useful after tests).
     */
    public static void resetMockConnection() {
        mockConnection = null;
    }
}
