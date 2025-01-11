package com.example.student_management_system.Utils;

import com.example.student_management_system.Models.Student;
import javafx.collections.ObservableList;

/**
 * Utility class for handling grade-related calculations.
 */
public class CalculateAverage {
    /**
     * Calculates the average grade for a list of students.
     *
     * @param students The list of students.
     * @return The average grade, or -1 if the list is empty.
     */
    public static double calculateAverageGrade(ObservableList<Student> students) {
        if (students == null || students.isEmpty()) {
            return -1; // Return -1 to indicate that there are no students.
        }

        double totalGrades = 0.0;
        for (Student student : students) {
            totalGrades += student.getGrade();
        }
        return totalGrades / students.size();
    }
}
