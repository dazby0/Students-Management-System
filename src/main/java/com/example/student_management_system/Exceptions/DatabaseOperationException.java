package com.example.student_management_system.Exceptions;

/**
 * Custom exception for cases where database operations fails.
 */
public class DatabaseOperationException extends Exception {
    /**
     * Constructs a new DatabaseOperationException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
