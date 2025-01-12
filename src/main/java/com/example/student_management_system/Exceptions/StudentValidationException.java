package com.example.student_management_system.Exceptions;

/**
 * Exception thrown when validation of student attributes fails.
 */
public class StudentValidationException extends RuntimeException {

    /**
     * Constructs a new StudentValidationException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the validation failure.
     */
    public StudentValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new StudentValidationException with the specified detail message and cause.
     *
     * @param message The detail message explaining the reason for the validation failure.
     * @param cause   The underlying cause of the validation failure.
     */
    public StudentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
