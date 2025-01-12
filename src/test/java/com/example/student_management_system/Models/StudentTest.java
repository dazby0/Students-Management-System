package com.example.student_management_system.Models;

import com.example.student_management_system.Exceptions.StudentValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Student class.
 */
class StudentTest {

    @Test
    void testValidStudentCreation() {
        // Test creating a valid student
        assertDoesNotThrow(() -> new Student("John Doe", 20, 85.5, "S12345"));

        Student student = new Student("John Doe", 20, 85.5, "S12345");
        assertEquals("John Doe", student.getName());
        assertEquals(20, student.getAge());
        assertEquals(85.5, student.getGrade());
        assertEquals("S12345", student.getStudentID());
    }

    @Test
    void testStudentCreationWithInvalidName() {
        // Test creating a student with an invalid name
        assertThrows(StudentValidationException.class, () -> new Student(null, 20, 85.5, "S12345"));
        assertThrows(StudentValidationException.class, () -> new Student("", 20, 85.5, "S12345"));
        assertThrows(StudentValidationException.class, () -> new Student("   ", 20, 85.5, "S12345"));
    }

    @Test
    void testStudentCreationWithInvalidAge() {
        // Test creating a student with an invalid age
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", 0, 85.5, "S12345"));
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", -5, 85.5, "S12345"));
    }

    @Test
    void testStudentCreationWithInvalidGrade() {
        // Test creating a student with an invalid grade
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", 20, -1.0, "S12345"));
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", 20, 101.0, "S12345"));
    }

    @Test
    void testStudentCreationWithInvalidStudentID() {
        // Test creating a student with an invalid student ID
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", 20, 85.5, null));
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", 20, 85.5, ""));
        assertThrows(StudentValidationException.class, () -> new Student("John Doe", 20, 85.5, "   "));
    }

    @Test
    void testSetName() {
        Student student = new Student("John Doe", 20, 85.5, "S12345");

        // Test setting a valid name
        assertDoesNotThrow(() -> student.setName("Jane Doe"));
        assertEquals("Jane Doe", student.getName());

        // Test setting an invalid name
        assertThrows(StudentValidationException.class, () -> student.setName(null));
        assertThrows(StudentValidationException.class, () -> student.setName(""));
        assertThrows(StudentValidationException.class, () -> student.setName("   "));
    }

    @Test
    void testSetAge() {
        Student student = new Student("John Doe", 20, 85.5, "S12345");

        // Test setting a valid age
        assertDoesNotThrow(() -> student.setAge(25));
        assertEquals(25, student.getAge());

        // Test setting an invalid age
        assertThrows(StudentValidationException.class, () -> student.setAge(0));
        assertThrows(StudentValidationException.class, () -> student.setAge(-10));
    }

    @Test
    void testSetGrade() {
        Student student = new Student("John Doe", 20, 85.5, "S12345");

        // Test setting a valid grade
        assertDoesNotThrow(() -> student.setGrade(95.0));
        assertEquals(95.0, student.getGrade());

        // Test setting an invalid grade
        assertThrows(StudentValidationException.class, () -> student.setGrade(-1.0));
        assertThrows(StudentValidationException.class, () -> student.setGrade(101.0));
    }

    @Test
    void testDisplayInfo() {
        // Test the displayInfo method (no assertion needed for console output)
        Student student = new Student("John Doe", 20, 85.5, "S12345");
        assertDoesNotThrow(student::displayInfo);
    }
}
