package com.example.student_management_system.Exceptions;

/**
 * Custom exception for cases where a form validation fails.
 */
public class ValidationException extends Exception {
    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
