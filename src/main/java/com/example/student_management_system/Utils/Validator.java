package com.example.student_management_system.Utils;

public class Validator {

    /**
     * Validates the fields for a student.
     *
     * @param studentID The ID of the student.
     * @param name      The name of the student.
     * @param ageText   The age of the student as a string.
     * @param gradeText The grade of the student as a string.
     * @throws IllegalArgumentException If any of the fields are invalid.
     */
    public static void validateStudentFields(String studentID, String name, String ageText, String gradeText) {
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (ageText == null || ageText.trim().isEmpty()) {
            throw new IllegalArgumentException("Age is required.");
        }
        if (gradeText == null || gradeText.trim().isEmpty()) {
            throw new IllegalArgumentException("Grade is required.");
        }

        try {
            int age = Integer.parseInt(ageText);
            if (age <= 0) {
                throw new IllegalArgumentException("Age must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be a valid integer.");
        }

        try {
            double grade = Double.parseDouble(gradeText);
            if (grade < 0.0 || grade > 100.0) {
                throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Grade must be a valid number.");
        }
    }
}
