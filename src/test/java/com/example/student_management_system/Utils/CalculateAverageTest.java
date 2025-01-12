package com.example.student_management_system.Utils;

import com.example.student_management_system.Models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateAverageTest {

    @Test
    void testCalculateAverageGradeWithValidStudents() {
        // Arrange: Create a list of students with valid grades
        ObservableList<Student> students = FXCollections.observableArrayList(
                new Student("Alice", 20, 90.5, "S001"),
                new Student("Bob", 21, 85.0, "S002"),
                new Student("Charlie", 22, 78.5, "S003")
        );

        // Act: Calculate the average grade
        double averageGrade = CalculateAverage.calculateAverageGrade(students);

        // Assert: Verify the average grade is correct
        assertEquals(84.67, averageGrade, 0.01, "The calculated average grade is incorrect.");
    }

    @Test
    void testCalculateAverageGradeWithEmptyList() {
        // Arrange: Create an empty list of students
        ObservableList<Student> students = FXCollections.observableArrayList();

        // Act: Calculate the average grade
        double averageGrade = CalculateAverage.calculateAverageGrade(students);

        // Assert: Verify the result is -1
        assertEquals(-1, averageGrade, "The result should be -1 for an empty list.");
    }

    @Test
    void testCalculateAverageGradeWithNullList() {
        // Arrange: A null list of students
        ObservableList<Student> students = null;

        // Act: Calculate the average grade
        double averageGrade = CalculateAverage.calculateAverageGrade(students);

        // Assert: Verify the result is -1
        assertEquals(-1, averageGrade, "The result should be -1 for a null list.");
    }

    @Test
    void testCalculateAverageGradeWithSingleStudent() {
        // Arrange: Create a list with a single student
        ObservableList<Student> students = FXCollections.observableArrayList(
                new Student("Alice", 20, 92.0, "S001")
        );

        // Act: Calculate the average grade
        double averageGrade = CalculateAverage.calculateAverageGrade(students);

        // Assert: Verify the average grade matches the single student's grade
        assertEquals(92.0, averageGrade, "The calculated average grade should match the single student's grade.");
    }
}
