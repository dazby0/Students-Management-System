package com.example.student_management_system.Utils;

import com.example.student_management_system.Exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ValidatorTest {

    @Test
    void testValidStudentFields() {
        // Valid input - should not throw any exceptions
        assertDoesNotThrow(() ->
                Validator.validateStudentFields("S123", "John Doe", "20", "85.5")
        );
    }

    @Test
    void testStudentIDIsNull() {
        // Null studentID - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields(null, "John Doe", "20", "85.5")
        );
        assert exception.getMessage().equals("Student ID is required.");
    }

    @Test
    void testStudentIDIsEmpty() {
        // Empty studentID - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("  ", "John Doe", "20", "85.5")
        );
        assert exception.getMessage().equals("Student ID is required.");
    }

    @Test
    void testNameIsNull() {
        // Null name - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", null, "20", "85.5")
        );
        assert exception.getMessage().equals("Name is required.");
    }

    @Test
    void testNameIsEmpty() {
        // Empty name - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "   ", "20", "85.5")
        );
        assert exception.getMessage().equals("Name is required.");
    }

    @Test
    void testAgeIsNull() {
        // Null ageText - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", null, "85.5")
        );
        assert exception.getMessage().equals("Age is required.");
    }

    @Test
    void testAgeIsEmpty() {
        // Empty ageText - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "  ", "85.5")
        );
        assert exception.getMessage().equals("Age is required.");
    }

    @Test
    void testAgeIsNotInteger() {
        // Invalid ageText - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "twenty", "85.5")
        );
        assert exception.getMessage().equals("Age must be a valid integer.");
    }

    @Test
    void testAgeIsNegative() {
        // Negative age - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "-1", "85.5")
        );
        assert exception.getMessage().equals("Age must be a positive integer.");
    }

    @Test
    void testGradeIsNull() {
        // Null gradeText - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "20", null)
        );
        assert exception.getMessage().equals("Grade is required.");
    }

    @Test
    void testGradeIsEmpty() {
        // Empty gradeText - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "20", "   ")
        );
        assert exception.getMessage().equals("Grade is required.");
    }

    @Test
    void testGradeIsNotDouble() {
        // Invalid gradeText - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "20", "eighty-five")
        );
        assert exception.getMessage().equals("Grade must be a valid number.");
    }

    @Test
    void testGradeIsOutOfRangeBelow() {
        // Grade below range - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "20", "-1")
        );
        assert exception.getMessage().equals("Grade must be between 0.0 and 100.0.");
    }

    @Test
    void testGradeIsOutOfRangeAbove() {
        // Grade above range - should throw ValidationException
        ValidationException exception = assertThrows(ValidationException.class, () ->
                Validator.validateStudentFields("S123", "John Doe", "20", "101")
        );
        assert exception.getMessage().equals("Grade must be between 0.0 and 100.0.");
    }
}
