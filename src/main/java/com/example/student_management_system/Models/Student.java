package com.example.student_management_system.Models;

import com.example.student_management_system.Exceptions.StudentValidationException;

/**
 * Represents a student entity with attributes for name, age, grade, and student ID.
 * Provides validation to ensure data integrity.
 */
public class Student {
    private String name;
    private int age;
    private double grade;
    private final String studentID;

    /**
     * Constructs a new Student instance with the specified attributes.
     *
     * @param name      The name of the student. Must not be null or empty.
     * @param age       The age of the student. Must be a positive integer.
     * @param grade     The grade of the student. Must be between 0.0 and 100.0.
     * @param studentID The unique identifier for the student. Must not be null or empty.
     * @throws StudentValidationException If any of the provided values are invalid.
     */
    public Student(String name, int age, double grade, String studentID) {
        setName(name);
        setAge(age);
        setGrade(grade);
        if (studentID == null || studentID.trim().isEmpty()) {
            throw new StudentValidationException("Student ID cannot be null or empty.");
        }
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new StudentValidationException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new StudentValidationException("Age must be a positive integer.");
        }
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new StudentValidationException("Grade must be between 0.0 and 100.0.");
        }
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    /**
     * Prints the details of the student to the console.
     */
    public void displayInfo() {
        System.out.println("Student Details:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Grade: " + grade);
        System.out.println("Student ID: " + studentID);
    }
}
