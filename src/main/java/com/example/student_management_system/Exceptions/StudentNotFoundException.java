package com.example.student_management_system.Exceptions;

/**
 * Custom exception for cases where a student is not found.
 */
public class StudentNotFoundException extends Exception {
    /**
     * Constructs a new StudentNotFoundException with the specified detail message.
     *
     * @param message The detail message.
     */
    public StudentNotFoundException(String message) {
        super(message);
    }
}
