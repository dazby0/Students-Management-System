package com.example.student_management_system.Database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testConnectSuccess() {
        // Act & Assert: Ensure the connection is successfully established
        try (Connection connection = DatabaseConnection.connect()) {
            assertNotNull(connection, "Connection should not be null.");
            assertFalse(connection.isClosed(), "Connection should be open.");
        } catch (SQLException e) {
            fail("Connection should not throw an exception, but it did: " + e.getMessage());
        }
    }

    @Test
    void testConnectInvalidUrl() {
        // Arrange: Temporarily modify the URL for testing
        String originalUrl = "jdbc:sqlite:students.db";
        String invalidUrl = "jdbc:invalid:students.db";

        // Act & Assert: Simulate a failure due to invalid URL
        try {
            // Using reflection to modify the private static field for testing
            java.lang.reflect.Field urlField = DatabaseConnection.class.getDeclaredField("URL");
            urlField.setAccessible(true);
            urlField.set(null, invalidUrl);

            // Attempt to connect with invalid URL
            assertThrows(SQLException.class, DatabaseConnection::connect, "Expected SQLException due to invalid URL.");

            // Restore the original URL
            urlField.set(null, originalUrl);
        } catch (Exception e) {
            fail("An unexpected exception occurred during test setup or execution: " + e.getMessage());
        }
    }
}
