package com.example.student_management_system.Exceptions;

/**
 * Custom exception for cases where a student with the same ID already exists.
 */
public class DuplicateStudentIDException extends Exception {

    /**
     * Constructs a new DuplicateStudentIDException with the specified detail message.
     *
     * @param message The detail message.
     */
    public DuplicateStudentIDException(String message) {
        super(message);
    }
}