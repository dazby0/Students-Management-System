package com.example.student_management_system.Exceptions;

/**
 * Custom exception for handling navigation errors in the application.
 */
public class NavigationException extends Exception {

    /**
     * Constructs a new NavigationException with the specified detail message.
     *
     * @param message The detail message.
     */
    public NavigationException(String message) {
        super(message);
    }

    /**
     * Constructs a new NavigationException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
